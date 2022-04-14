package com.joker.videoCache.tasks

import android.os.Environment
import android.util.Log
import com.joker.videoCache.CacheEvent
import com.joker.videoCache.LocalCacheTaskManager
import com.joker.videoCache.interfaces.ICacheTask
import com.safframework.statemachine.StateMachine
import com.safframework.statemachine.model.BaseEvent
import com.safframework.statemachine.model.BaseState
import com.tencent.mars.xlog.Xlog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.withLock


/**
 * @ClassName BaseTask
 * @Description 简单的task的状态机实现
 * @Author huangziwen
 * @Date 2022/3/28 4:22 下午
 */


sealed class CacheState : BaseState() {
    class Idle : CacheState()
    class Running : CacheState()
    class Pause : CacheState()
    class End : CacheState()
}

sealed class CacheStateEvent : BaseEvent() {
    class StartTask : CacheStateEvent()
    class PauseTask : CacheStateEvent()
    class ResumeTask : CacheStateEvent()
    class FinishTask : CacheStateEvent()
    class ForceEndTask : CacheStateEvent()
}

abstract class BaseTask : ICacheTask {
    companion object {
        val TAG: String = "BaseTask"
    }

    lateinit var url: String

    private val stateMachine: StateMachine by lazy {
        StateMachine.buildStateMachine(name = url, initialStateName = CacheState.Idle()) {
            state(CacheState.Idle()) {
                entry {
                    stateAction { _, _ ->
                        LocalCacheTaskManager.eventBus.post(CacheEvent.CreateNewCacheTask(this@BaseTask))
                        Log.d(TAG, "$name create task")
                    }
                }
                transition(CacheStateEvent.StartTask(), CacheState.Running()) {}
                transition(CacheStateEvent.ForceEndTask(), CacheState.End()) {
                    tranAction { _, _ ->
                        //Todo
                    }
                }
            }

            state(CacheState.Running()) {
                entry {
                    stateAction { _, stateScope ->
                        Log.d(TAG, "$name running task")
                        LocalCacheTaskManager.eventBus.post(CacheEvent.ResumeCacheTask(this@BaseTask))
                        stateScope.launch {
                            //Todo runing状态需要做的事
                            stateMachine.lock.withLock {
                                sendEvent(CacheStateEvent.FinishTask())
                            }
                        }
                    }
                }
                transition(CacheStateEvent.FinishTask(), CacheState.End()) {
                    tranAction { _, _ ->
                        //Todo
                    }
                }

                transition(CacheStateEvent.PauseTask(), CacheState.Pause()) {
                    tranAction { _, _ ->
                        //Todo
                    }
                }

                transition(CacheStateEvent.ForceEndTask(), CacheState.End()) {
                    tranAction { _, _ ->
                        //Todo
                    }
                }

            }

            state(CacheState.Pause()) {
                entry {
                    stateAction { _, _ ->
                        LocalCacheTaskManager.eventBus.post(CacheEvent.PauseCacheTask(this@BaseTask))
                        Log.d(TAG, "$name pause task")
                    }
                }
                transition(CacheStateEvent.FinishTask(), CacheState.End()) {
                    tranAction { _, _ ->
                        //Todo
                    }
                }

                transition(CacheStateEvent.ResumeTask(), CacheState.Running()) {
                    tranAction { _, _ ->
                        //Todo
                    }
                }

                transition(CacheStateEvent.ForceEndTask(), CacheState.End()) {
                    tranAction { _, _ ->
                        //Todo
                    }
                }

            }

            state(CacheState.End()) {
                entry {
                    stateAction { _, _ ->
                        LocalCacheTaskManager.eventBus.post(CacheEvent.RemoveCacheTask(this@BaseTask))
                        Log.d(TAG, "$name end task")
                    }
                }

                transition(CacheStateEvent.ForceEndTask(), CacheState.End()) {
                    tranAction { _, _ ->
                        //Todo
                    }
                }
            }
        }
    }



    override fun start() {
        sendEventWithLock {
            stateMachine.sendEvent(CacheStateEvent.StartTask())
        }
    }

    override fun resume() {
        sendEventWithLock {
            stateMachine.sendEvent(CacheStateEvent.ResumeTask())
        }
    }

    override fun pause() {
        sendEventWithLock {
            stateMachine.sendEvent(CacheStateEvent.PauseTask())
        }
    }

    override fun end() {
        sendEventWithLock {
            stateMachine.sendEvent(CacheStateEvent.ForceEndTask())
        }
    }

    override fun seekToSyncCacheTaskFromClient(percent: Float) {
        TODO("Not yet implemented")
    }

    override fun run() {
        TODO("Not yet implemented")
    }

    private fun sendEventWithLock(  sendBlock:suspend ()-> Unit){
        stateMachine.stateScope.launch {
            stateMachine.lock.withLock {
                sendBlock()
            }
        }
    }
}

