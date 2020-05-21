package com.guerra.enrico.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.guerra.enrico.navigation.directions.ActivityDirection
import com.guerra.enrico.navigation.directions.FragmentDirection
import com.guerra.enrico.navigation.directions.WithParams
import com.guerra.enrico.navigation.directions.WithResult
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
    // TODO
  }

  override fun startActivityForResult(
    fragment: Fragment,
    direction: ActivityDirection,
    options: Bundle?
  ) {
    if (direction !is WithResult) {
      throw IllegalArgumentException("Destination ${direction.destination} doesn't support result")
    }
    navigationController.startActivityForResult(
      fragment,
      direction.destination,
      direction.code,
      options
    )
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
}