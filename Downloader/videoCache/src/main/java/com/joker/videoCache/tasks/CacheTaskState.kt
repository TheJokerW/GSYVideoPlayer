package com.joker.videoCache.tasks

/**
 * @ClassName CacheTaskState
 * @Description TODO
 * @Author huangziwen
 * @Date 2022/3/30 3:42 下午
 */
enum class CacheTaskState {
    IDLE,//task build but not be put in TaskManager
//    READY,//has been put in TaskManager,preparing to run
    RUNNING,//parsing or caching
    PAUSED,
    END,
}
