package com.joker.videoCache

import android.util.Log
import com.jeffmony.videocache.VideoLockManager
import com.jeffmony.videocache.model.VideoCacheInfo
import com.jeffmony.videocache.utils.ProxyCacheUtils
import com.jeffmony.videocache.utils.VideoProxyThreadUtils
import com.joker.videoCache.interfaces.ICacheTask
import com.joker.videoCache.interfaces.ICacheTaskManager
import com.joker.videoCache.interfaces.IUrlParser
import com.joker.videoCache.parser.DefaultParser
import com.joker.videoCache.tasks.BaseTask
import com.joker.videoCache.tasks.M3u8CacheTask
import com.joker.videoCache.tasks.Mp4CacheTask
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.ConcurrentHashMap

/**
 * @ClassName LocalCacheTaskManager
 * @Description TODO
 * @Author huangziwen
 * @Date 2022/3/25 3:16 下午
 */

sealed class CacheEvent {
    class StartNewCacheTask(task: BaseTask) : CacheEvent()
    class ResumeCacheTask(task: BaseTask) : CacheEvent()
    class PauseCacheTask(task: BaseTask) : CacheEvent()
    class RemoveCacheTask(task: BaseTask) : CacheEvent()
}

class LocalCacheTaskManager : ICacheTaskManager {

    companion object {
        const val TAG = "LocalCacheTaskManager"

        @JvmStatic
        val instance: LocalCacheTaskManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            LocalCacheTaskManager().apply {
                register(VideoType.M3U8, M3u8CacheTask::class.java)
                register(VideoType.MP4, Mp4CacheTask::class.java)
                eventBus.register(this)
            }
        }

        //所有的事件都需要走这个事件总线，方便task之间的交互
        val eventBus: EventBus by lazy {
            EventBus.builder().build()
        }

        fun lockWithUrl(url: String, closure: () -> Unit) {
            //用url创建锁，防止出现创建不同的task，或者重复创建task，且可能出现野task，保证一个生命周期只有一个url对应的task实例
            synchronized(VideoLockManager.getInstance().getLock(ProxyCacheUtils.computeMD5(url))) {
                closure()
            }
        }
    }

    //管理所有Task的
    private val cacheTaskMap: ConcurrentHashMap<String, BaseTask> = ConcurrentHashMap()
    private val cacheInfoMap: ConcurrentHashMap<String, VideoCacheInfo> = ConcurrentHashMap()

    //kotlin 的enum class 只有一个实例
    private val typeTaskMap: ConcurrentHashMap<VideoType, Class<out BaseTask>> =
        ConcurrentHashMap()


    private var urlParser: IUrlParser = DefaultParser()


    override fun register(type: VideoType, taskClazz: Class<out BaseTask>) {
        typeTaskMap[type] = taskClazz
    }

    override fun registerUrlParser(parser: IUrlParser) {
        urlParser = parser
    }

    private fun getTask(url: String): BaseTask? {
        var task = cacheTaskMap[url]
        if (task == null) {
            //这里解析是耗时操作
            val type = urlParser.typeConvertCacheTask(url)
            val taskType = typeTaskMap[type]
            val taskName = taskType?.simpleName ?: ""
            if (taskType == null) {
                Log.e(TAG, "createTask:can't create task type:$type taskName:$taskName")
                return null
            }
            task = taskType.newInstance()
            task.url = url
            if (task == null) {
                Log.e(TAG, "createTask:can't create task type:$type taskName:$taskName")
            }
        }
        return task
    }


    override fun submitCacheTask(url: String) {
        lockWithUrl(url) {
            getTask(url)?.let {
                cacheTaskMap[url] = it
                it.start()
            }

        }
    }

    override fun resumeCacheTask(url: String) {
        cacheTaskMap[url]?.resume()
    }

    override fun pauseCacheTask(url: String) {
        cacheTaskMap[url]?.pause()
    }

    override fun removeCacheTask(url: String) {
        lockWithUrl(url) {
            cacheTaskMap[url]?.let {
                cacheTaskMap.remove(url)
                it.forceEnd()
            }
            VideoLockManager.getInstance().removeLock(ProxyCacheUtils.computeMD5(url))
        }
    }

    override fun seekToSyncCacheTaskFromClient(url: String, percent: Float) {
        cacheTaskMap[url]?.seekToSyncCacheTaskFromClient(percent)
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    private fun onMessageEvent(event: CacheEvent) {
        //使用eventbus 主要是将一些复杂的数据结构，同步
    }

    fun submitCacheTaskAsync(url: String) {
        VideoProxyThreadUtils.submitRunnableTask {
            submitCacheTask(url)
        }
    }

}
