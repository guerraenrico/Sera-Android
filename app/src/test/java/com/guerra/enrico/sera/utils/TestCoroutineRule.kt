package com.guerra.enrico.sera.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * Created by enrico
 * on 07/12/2019.
 */
class TestCoroutineRule(val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()) :
  TestWatcher() {

  override fun starting(description: Description?) {
    super.starting(description)
    Dispatchers.setMain(dispatcher)
  }

  override fun finished(description: Description?) {
    super.finished(description)
    Dispatchers.resetMain()
    dispatcher.cleanupTestCoroutines()
  }

  fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) =
    dispatcher.runBlockingTest(block)
}