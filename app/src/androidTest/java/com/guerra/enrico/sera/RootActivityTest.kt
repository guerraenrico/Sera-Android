package com.guerra.enrico.sera

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.guerra.enrico.sera.di.module.CoroutineDispatcherModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by enrico
 * on 05/07/2020.
 */
@HiltAndroidTest
@UninstallModules(CoroutineDispatcherModule::class)
@RunWith(AndroidJUnit4::class)
class RootActivityTest {
  @get:Rule(order = 0)
  var hiltRule = HiltAndroidRule(this)

  @get:Rule(order = 1)
  var activityIntentRule = ActivityScenarioRule(RootActivity::class.java)

//  @BindValue
//  var observeCategories: ObserveCategories = mockk()

  @Test
  fun testLoadTodosFragment() {
    onView(withId(R.id.container)).check(matches(isDisplayed()))
  }

}