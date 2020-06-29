package com.guerra.enrico.base_android.arch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.runner.RunWith
import androidx.test.rule.ActivityTestRule
import com.guerra.enrico.base_android.utils.SingleFragmentActivity
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

/**
 * Created by enrico
 * on 27/05/2020.
 */
@RunWith(AndroidJUnit4::class)
class AutoClearValueTest {

  @Rule
  @JvmField
  val activityRule = ActivityTestRule(SingleFragmentActivity::class.java,  true, true)

  private lateinit var testFragment: TestFragment

  @Before
  fun setup() {
    testFragment = TestFragment()
    activityRule.activity.setFragment(testFragment)
    InstrumentationRegistry.getInstrumentation().waitForIdleSync()
  }

  @Test
  fun testErrorWhenAccessIfNotSet() {
    try {
      testFragment.testValue
      Assert.fail("should throw if access when not set")
    } catch (e: IllegalStateException) {}
  }

  @Test
  fun testClearOnReplace() {
    testFragment.testValue = "foo"
    activityRule.activity.replaceFragment(TestFragment())
    InstrumentationRegistry.getInstrumentation().waitForIdleSync()

    try {
      testFragment.testValue
      Assert.fail("should throw if access when not set")
    } catch (e: IllegalStateException) {}
  }

  @Test
  fun testClearOnReplaceBackStack() {
    testFragment.testValue = "foo"
    activityRule.activity.replaceFragment(TestFragment(), addToBackStack = true)
    InstrumentationRegistry.getInstrumentation().waitForIdleSync()

    try {
      testFragment.testValue
      Assert.fail("should throw if access when not set")
    } catch (e: IllegalStateException) {}

    activityRule.activity.supportFragmentManager.popBackStack()
    InstrumentationRegistry.getInstrumentation().waitForIdleSync()
    assertEquals("foo", testFragment.testValue)
  }

  @Test
  fun testDontClearOnAddChildFragment() {
    testFragment.testValue = "foo"
    testFragment.childFragmentManager.beginTransaction().add(Fragment(), "child").commit()
    InstrumentationRegistry.getInstrumentation().waitForIdleSync()
    assertEquals("foo", testFragment.testValue)
  }

  @Test
  fun testDontClearOnDialog() {
    testFragment.testValue = "foo"
    val dialogFragment = DialogFragment()
    dialogFragment.show(testFragment.parentFragmentManager, "dialog")
    dialogFragment.dismiss()
    InstrumentationRegistry.getInstrumentation().waitForIdleSync()
    assertEquals("foo", testFragment.testValue)
  }

  private class TestFragment : Fragment() {
    var testValue: String by autoClearValue()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
      return View(context)
    }
  }
}