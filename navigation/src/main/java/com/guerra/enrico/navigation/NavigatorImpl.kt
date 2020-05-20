package com.guerra.enrico.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.guerra.enrico.navigation.di.ActivityDestination
import com.guerra.enrico.navigation.di.FragmentDestination
import com.guerra.enrico.navigation.directions.ActivityDirection
import com.guerra.enrico.navigation.directions.FragmentDirection
import com.guerra.enrico.navigation.directions.WithParams
import javax.inject.Inject

/**
 * Created by enrico
 * on 16/05/2020.
 */
internal class NavigatorImpl @Inject constructor(
  private val navigationController: NavigationController
) : Navigator {

  override fun startActivity(
    activity: FragmentActivity,
    direction: ActivityDirection,
    options: Bundle?
  ) {
    val bundle = Bundle(options)
    if (direction is WithParams<*>) {
      bundle.putParcelable(direction.key, direction.params)
    }
    navigationController.startActivity(activity, direction.destination, bundle)
  }

  override fun startActivityForResult(
    activity: FragmentActivity,
    direction: ActivityDirection,
    options: Bundle?
  ) {
    TODO("Not yet implemented")
  }

  override fun replaceFragment(
    fragmentManager: FragmentManager,
    containerId: Int,
    direction: FragmentDirection
  ) {
    if (direction is WithParams<*>) {
      // TODO handle pass parameter to fragment
    }
    navigationController.replaceFragment(fragmentManager, containerId, direction.destination)
  }

  override fun startLoginActivity(activity: FragmentActivity, options: Bundle?) {
    navigationController.startActivity(activity, ActivityDestination.LOGIN, options)
  }

  override fun startMainActivity(activity: FragmentActivity, options: Bundle?) {
    navigationController.startActivity(activity, ActivityDestination.MAIN, options)
  }

  override fun startTodoSearchActivityForResult(fragment: Fragment, options: Bundle?) {
    navigationController.startActivityForResult(
      fragment,
      ActivityDestination.TODOS_SEARCH,
      TODO_SEARCH_REQUEST_CODE,
      options
    )
  }

  override fun startTodoAddActivity(activity: FragmentActivity, options: Bundle?) {
    navigationController.startActivity(activity, ActivityDestination.TODOS_ADD, options)
  }

  override fun replaceWithLoginFragment(fragmentManager: FragmentManager, containerId: Int) {
    navigationController.replaceFragment(fragmentManager, containerId, FragmentDestination.LOGIN)
  }

  override fun replaceWithLoginSyncFragment(fragmentManager: FragmentManager, containerId: Int) {
    navigationController.replaceFragment(fragmentManager, containerId, FragmentDestination.LOGIN_SYNC)
  }

  override fun replaceWithTodosFragment(fragmentManager: FragmentManager, containerId: Int) {
    navigationController.replaceFragment(fragmentManager, containerId, FragmentDestination.TODOS)
  }

  override fun replaceWithTodoAddSelectFragment(
    fragmentManager: FragmentManager,
    containerId: Int
  ) {
    navigationController.replaceFragment(
      fragmentManager,
      containerId,
      FragmentDestination.TODO_ADD_SELECT
    )
  }

  override fun replaceWithTodoAddAddCategoryFragment(
    fragmentManager: FragmentManager,
    containerId: Int
  ) {
    navigationController.replaceFragment(
      fragmentManager,
      containerId,
      FragmentDestination.TODO_ADD_ADD_CATEGORY
    )
  }

  override fun replaceWithTodoAddSelectCategoryFragment(
    fragmentManager: FragmentManager,
    containerId: Int
  ) {
    navigationController.replaceFragment(
      fragmentManager,
      containerId,
      FragmentDestination.TODO_ADD_SELECT_CATEGORY
    )
  }

  override fun replaceWithTodoAddAddTaskFragment(
    fragmentManager: FragmentManager,
    containerId: Int
  ) {
    navigationController.replaceFragment(
      fragmentManager,
      containerId,
      FragmentDestination.TODO_ADD_ADD_TASK
    )
  }

  override fun replaceWithTodoAddScheduleFragment(
    fragmentManager: FragmentManager,
    containerId: Int
  ) {
    navigationController.replaceFragment(
      fragmentManager,
      containerId,
      FragmentDestination.TODO_ADD_SCHEDULE
    )
  }

  override fun replaceWithTodoAddDoneFragment(fragmentManager: FragmentManager, containerId: Int) {
    navigationController.replaceFragment(
      fragmentManager,
      containerId,
      FragmentDestination.TODO_ADD_DONE
    )
  }

  override fun replaceWithGoalsFragment(fragmentManager: FragmentManager, containerId: Int) {
    navigationController.replaceFragment(fragmentManager, containerId, FragmentDestination.GOALS)
  }

  override fun replaceWithResultsFragment(fragmentManager: FragmentManager, containerId: Int) {
    navigationController.replaceFragment(fragmentManager, containerId, FragmentDestination.RESULTS)
  }

  override fun replaceWithSettingsFragment(fragmentManager: FragmentManager, containerId: Int) {
    navigationController.replaceFragment(fragmentManager, containerId, FragmentDestination.SETTINGS)
  }

}