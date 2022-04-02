package com.joker.videoCache.interfaces

import com.joker.videoCache.VideoType
import com.joker.videoCache.tasks.BaseTask

/**
 * @ClassName ICacheTaskManager
 * @Description TODO
 * @Author huangziwen
 * @Date 2022/3/25 3:23 下午
 */
interface ICacheTaskManager {

    fun register(type: VideoType, taskClazz: Class<out BaseTask>)
    fun registerUrlParser(parser: IUrlParser)
    fun submitCacheTask(url: String)
    fun resumeCacheTask(url: String)
    fun pauseCacheTask(url: String)
    fun removeCacheTask(url: String)
    fun seekToSyncCacheTaskFromClient(url: String, percent: Float)
}
