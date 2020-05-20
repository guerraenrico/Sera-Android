package com.guerra.enrico.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.guerra.enrico.navigation.directions.ActivityDirection
import com.guerra.enrico.navigation.directions.FragmentDirection

/**
 * Created by enrico
 * on 16/05/2020.
 */
interface Navigator {
  fun startActivity(activity: FragmentActivity, direction: ActivityDirection, options: Bundle? = null)
  fun startActivityForResult(activity: FragmentActivity, direction: ActivityDirection, options: Bundle? = null)

  fun replaceFragment(fragmentManager: FragmentManager, @IdRes containerId: Int, direction: FragmentDirection)

  fun startLoginActivity(activity: FragmentActivity, options: Bundle? = null)
  fun startMainActivity(activity: FragmentActivity, options: Bundle? = null)
  fun startTodoSearchActivityForResult(fragment: Fragment, options: Bundle? = null)
  fun startTodoAddActivity(activity: FragmentActivity, options: Bundle? = null)

  fun replaceWithLoginFragment(fragmentManager: FragmentManager, @IdRes containerId: Int)
  fun replaceWithLoginSyncFragment(fragmentManager: FragmentManager, @IdRes containerId: Int)

  fun replaceWithTodosFragment(fragmentManager: FragmentManager, @IdRes containerId: Int)
  fun replaceWithTodoAddSelectFragment(fragmentManager: FragmentManager, @IdRes containerId: Int)
  fun replaceWithTodoAddAddCategoryFragment(fragmentManager: FragmentManager, @IdRes containerId: Int)
  fun replaceWithTodoAddSelectCategoryFragment(fragmentManager: FragmentManager, @IdRes containerId: Int)
  fun replaceWithTodoAddAddTaskFragment(fragmentManager: FragmentManager, @IdRes containerId: Int)
  fun replaceWithTodoAddScheduleFragment(fragmentManager: FragmentManager, @IdRes containerId: Int)
  fun replaceWithTodoAddDoneFragment(fragmentManager: FragmentManager, @IdRes containerId: Int)


  fun replaceWithGoalsFragment(fragmentManager: FragmentManager, @IdRes containerId: Int)
  fun replaceWithResultsFragment(fragmentManager: FragmentManager, @IdRes containerId: Int)
  fun replaceWithSettingsFragment(fragmentManager: FragmentManager, @IdRes containerId: Int)
}