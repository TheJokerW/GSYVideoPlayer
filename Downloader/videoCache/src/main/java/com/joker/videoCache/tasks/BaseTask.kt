package com.joker.videoCache.tasks

import com.joker.videoCache.CacheEvent
import com.joker.videoCache.LocalCacheTaskManager
import com.joker.videoCache.interfaces.ICacheTask

/**
 * @ClassName BaseTask
 * @Description TODO
 * @Author huangziwen
 * @Date 2022/3/28 4:22 下午
 */

abstract class BaseTask: ICacheTask {

    protected var state:CacheTaskState = CacheTaskState.IDLE
    lateinit var url:String


    override fun start() {
        if(state != CacheTaskState.IDLE){
            return
        }
        state = CacheTaskState.RUNNING
        LocalCacheTaskManager.eventBus.post(CacheEvent.StartNewCacheTask(this))
    }

    override fun resume() {

        state = CacheTaskState.RUNNING
        LocalCacheTaskManager.eventBus.post(CacheEvent.ResumeCacheTask(this))
    }

    override fun pause() {
        state = CacheTaskState.PAUSED
        LocalCacheTaskManager.eventBus.post(CacheEvent.PauseCacheTask(this))
    }

    override fun end() {
        state = CacheTaskState.END
        LocalCacheTaskManager.eventBus.post(CacheEvent.RemoveCacheTask(this))
    }

    override fun seekToSyncCacheTaskFromClient(percent: Float) {
        TODO("Not yet implemented")
    }

    override fun run() {
        TODO("Not yet implemented")
    }
}
