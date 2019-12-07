package com.guerra.enrico.sera

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.guerra.enrico.sera.utils.CoroutineContextProviderTest
import com.guerra.enrico.sera.utils.TestCoroutineRule
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Created by enrico
 * on 07/12/2019.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
abstract class BaseTest {
  @Rule
  @JvmField
  val mockitoRule: MockitoRule = MockitoJUnit.rule()

  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  val testCoroutineRule = TestCoroutineRule()
  val coroutineContextProviderTest = CoroutineContextProviderTest()
}