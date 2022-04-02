package com.joker.videoCache.tasks

import com.joker.videoCache.interfaces.ICacheTask

/**
 * @ClassName BaseTask
 * @Description TODO
 * @Author huangziwen
 * @Date 2022/3/28 4:22 下午
 */

abstract class BaseTask: ICacheTask {

    var state:CacheTaskState = CacheTaskState.IDLE

    override fun start() {
        TODO("Not yet implemented")
    }

    override fun resume() {
        TODO("Not yet implemented")
    }

    override fun pause() {
        TODO("Not yet implemented")
    }

    override fun remove() {
        TODO("Not yet implemented")
    }

    override fun seekToSyncCacheTaskFromClient(percent: Float) {
        TODO("Not yet implemented")
    }

    override fun run() {
        TODO("Not yet implemented")
    }
}
