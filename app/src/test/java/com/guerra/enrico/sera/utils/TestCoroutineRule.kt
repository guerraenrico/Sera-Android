package com.guerra.enrico.sera.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement


/**
 * Created by enrico
 * on 07/12/2019.
 */
class TestCoroutineRule: TestRule {
  private val testCoroutineDispatcher = TestCoroutineDispatcher()
  private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

  override fun apply(base: Statement, description: Description?): Statement = object : Statement() {
    override fun evaluate() {
      Dispatchers.setMain(testCoroutineDispatcher)
      base.evaluate()
      Dispatchers.resetMain()
      testCoroutineScope.cleanupTestCoroutines()
    }
  }

  fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) =
          testCoroutineScope.runBlockingTest(block)
}