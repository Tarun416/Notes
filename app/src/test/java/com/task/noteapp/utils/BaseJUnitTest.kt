package com.task.noteapp.utils

import org.junit.After
import org.junit.Before

abstract class BaseJUnitTest {
    private val trampoline = TrampolineSchedulerRule()

    abstract fun start()
    abstract fun stop()

    @Before
    fun setup() {
        trampoline.start()
        MonkInstantTaskExecutorRule.start()
        start()
    }

    @After
    fun tearDown() {
        stop()
        MonkInstantTaskExecutorRule.tearDown()
        trampoline.tearDown()
    }
}
