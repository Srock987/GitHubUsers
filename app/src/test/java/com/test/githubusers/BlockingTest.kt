package com.test.githubusers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.githubusers.presentation.corutineUtil.DispatcherProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.rules.TestWatcher
import org.junit.runner.Description


@ExperimentalCoroutinesApi
open class BlockingTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesRule = CoroutinesTestRule()

    @ExperimentalCoroutinesApi
    protected val testDispatcher = TestCoroutineDispatcher()

    val dispatcherProvider =
        DispatcherProvider(
            testDispatcher,
            testDispatcher
        )

    protected fun runSuspendTestBlock(block: suspend () -> Unit) {
        coroutinesRule.testDispatcher.runBlockingTest { block.invoke() }
    }
}

@ExperimentalCoroutinesApi
class CoroutinesTestRule(
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}