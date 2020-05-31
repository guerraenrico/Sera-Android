package com.guerra.enrico.navigation

import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.guerra.enrico.navigation.di.ActivityTarget
import com.guerra.enrico.navigation.di.Destination
import com.guerra.enrico.navigation.di.FragmentTarget
import com.guerra.enrico.navigation.directions.ActivityDirection
import com.guerra.enrico.navigation.directions.FragmentDirection
import com.guerra.enrico.navigation.directions.Input
import com.guerra.enrico.navigation.directions.Output
import dagger.multibindings.ClassKey
import javax.inject.Inject

/**
 * Created by enrico
 * on 16/05/2020.
 */
internal class NavigatorImpl @Inject constructor(
  private val activities: Map<ActivityTarget, Destination>, //
  private val fragments: Map<FragmentTarget, Destination>,
  private val navigationController: NavigationController
) : Navigator {

  override fun startActivity(
    activity: FragmentActivity,
    direction: ActivityDirection,
    options: ActivityOptionsCompat?
  ) {
    val destinationInfo = activities[direction.target]
      ?: throw IllegalArgumentException("No package provided for this destination:${direction.target}")

    val clazz = Class.forName(destinationInfo.className)
    val intent = Intent(Intent.ACTION_VIEW).setClass(activity, clazz)

    val bundle = Bundle(options?.toBundle())
    if (direction is Input<*>) {
      bundle.putParcelable(direction.key, direction.params)
    }


    navigationController.startActivity(activity, intent, bundle)
  }

  override fun startActivityForResult(
    activity: FragmentActivity,
    direction: ActivityDirection,
    options: ActivityOptionsCompat?
  ) {
    val destinationInfo = activities[direction.target]
      ?: throw IllegalArgumentException("No package provided for this destination:$direction.destination")

    val clazz = Class.forName(destinationInfo.className)
    val intent = Intent(Intent.ACTION_VIEW).setClass(activity, clazz)
  }

  override fun startActivityForResult(
    fragment: Fragment,
    direction: ActivityDirection,
    options: ActivityOptionsCompat?
  ) {
    val destinationInfo = activities[direction.target]
      ?: throw IllegalArgumentException("No package provided for this destination:$direction.destination")

    val clazz = Class.forName(destinationInfo.className)
    val intent = Intent(Intent.ACTION_VIEW).setClass(fragment.requireContext(), clazz)

    if (direction !is Output) {
      throw IllegalArgumentException("Destination ${direction.target} doesn't support result")
    }

    navigationController.startActivityForResult(
      fragment,
      intent,
      options
    )
  }

  override fun replaceFragment(
    fragmentManager: FragmentManager,
    containerId: Int,
    direction: FragmentDirection
  ) {
    val destinationInfo = fragments[direction.target]
      ?: throw IllegalArgumentException("No package provided for this destination:$direction.destination")

    val fragment: Fragment = Class.forName(destinationInfo.className).newInstance() as Fragment

    if (direction is Input<*>) {
      // TODO handle pass parameter to fragment
    }
    navigationController.replaceFragment(fragmentManager, containerId, fragment)
  }
}