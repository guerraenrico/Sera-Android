package com.guerra.enrico.sera

import org.junit.Rule
import org.mockito.junit.MockitoJUnit

/**
 * Created by enrico
 * on 03/09/2019.
 */
abstract class BaseViewModelTest : BaseDatabaseTest() {
  @Rule
  @JvmField
  val mochitoRule = MockitoJUnit.rule()
}