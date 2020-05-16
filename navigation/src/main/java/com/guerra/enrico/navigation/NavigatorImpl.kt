package com.guerra.enrico.navigation

import android.content.Intent
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.guerra.enrico.navigation.di.ActivityDestination
import com.guerra.enrico.navigation.di.DestinationInfo
import com.guerra.enrico.navigation.di.FragmentDestination
import javax.inject.Inject

/**
 * Created by enrico
 * on 16/05/2020.
 */
internal class NavigatorImpl @Inject constructor(
  private val activityDestinations: Map<ActivityDestination, DestinationInfo>,
  private val fragmentDestinations: Map<FragmentDestination, DestinationInfo>
) : Navigator {

  override fun openActivity(activity: FragmentActivity, destination: ActivityDestination) {
    val destinationInfo = activityDestinations[destination]
      ?: throw IllegalArgumentException("No package provided for this destination:$destination")

    val clazz = Class.forName(destinationInfo.className)
    val intent = Intent(Intent.ACTION_VIEW).setClass(activity, clazz)
    activity.startActivity(intent)
  }

  override fun replaceFragment(
    fragmentManager: FragmentManager,
    @IdRes containerId: Int,
    destination: FragmentDestination
  ) {
    val destinationInfo = fragmentDestinations[destination]
      ?: throw IllegalArgumentException("No package provided for this destination:$destination")

    val fragment: Fragment = Class.forName(destinationInfo.className).newInstance() as Fragment
    fragmentManager.beginTransaction().apply {
      replace(containerId, fragment)
      commit()
    }
  }

}