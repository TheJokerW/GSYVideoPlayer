package com.joker.videoCache.interfaces

/**
 * @ClassName ICacheTask
 * @Description TODO
 * @Author huangziwen
 * @Date 2022/3/25 3:41 下午
 */
interface ICacheTask:Runnable {
    fun start()
    fun resume()
    fun pause()
    fun remove()
    fun seekToSyncCacheTaskFromClient(percent: Float)
}
