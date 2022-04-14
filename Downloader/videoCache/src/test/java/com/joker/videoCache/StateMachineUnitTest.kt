package com.joker.videoCache

import com.safframework.statemachine.StateMachine
import com.safframework.statemachine.model.BaseEvent
import com.safframework.statemachine.model.BaseState
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * @ClassName StateMachineUnitTest
 * @Description TODO
 * @Author huangziwen
 * @Date 2022/4/13 12:12 下午
 */
class StateMachineUnitTest {


    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun testSomeThing() = runTest {
        test()
    }

    sealed class TestState: BaseState() {
        class Initial: TestState()
        class Eat: TestState()
        class WatchTV: TestState()
    }


    sealed class TestEvent: BaseEvent() {
        class Cook: BaseEvent()
        class WashDishes: BaseEvent()
    }

    suspend fun test(){
        val sm = StateMachine.buildStateMachine(name = "test",initialStateName = TestState.Initial()) {

            state(TestState.Initial()) {

                entry {
                    stateAction { state,_ ->
                        println("Entered [${state.name}] State")
                    }
                }

                transition(TestEvent.Cook(), TestState.Eat()) {

                    tranAction(Dispatchers.IO){ _,_ ->
                        for (i in 1..1000){
                            println("Action: Wash Vegetables$i")
                        }
                    }

                    tranAction { _,_ ->
                            println("Action: Cook")
                    }
                }

                exit {
                    stateAction { state,_ ->
                        println("Exited [${state.name}] State")
                    }
                }
            }

            state(TestState.Eat()) {

                entry{
                    stateAction { state,_ ->
                        println("Entered [${state.name}] State")
                    }
                }

                transition(TestEvent.WashDishes(), TestState.WatchTV()) {

                    tranAction {  _,scope ->
                        for(i in 1..1000){

                            println("Action: Wash Dishes$i active:${scope.isActive}")
                        }
                    }

                    tranAction { _,_ ->
                        println("Action: Turn on the TV")
                    }
                }
            }

            state(TestState.WatchTV()) {

                entry{
                    stateAction { state,_ ->
                        println("Entered [${state.name}] State")

                    }
                }
            }
        }
        sm.initialize()
        val test1 = sm.stateScope.async {
            sm.lock.withLock{
                delay(500)
                sm.sendEvent(TestEvent.Cook())
            }

        }
        val test2 = sm.stateScope.async {
            sm.lock.withLock{
                sm.sendEvent(TestEvent.WashDishes())
            }
        }


        test1.await()
        sm.release()
        try{
            test2.await()
        }catch(e: Exception){
            e.printStackTrace()
        }


    }
}
