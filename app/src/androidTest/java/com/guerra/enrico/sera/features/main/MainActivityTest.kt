package com.guerra.enrico.sera.features.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.guerra.enrico.domain.observers.todos.ObserveCategories
import com.guerra.enrico.main.MainActivity
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.sera.R
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by enrico
 * on 05/07/2020.
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
  @get:Rule(order = 0)
  var hiltRule = HiltAndroidRule(this)

  @get:Rule(order = 1)
  var activityIntentRule = ActivityScenarioRule(MainActivity::class.java)

  @BindValue
  var observeCategories: ObserveCategories = mockk()

  @Test
  fun testLoadTodosFragment() {
    observeCategories.toString()
    onView(withId(R.id.todos_container)).check(matches(isDisplayed()))
  }

}