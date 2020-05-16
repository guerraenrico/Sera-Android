package com.guerra.enrico.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.guerra.enrico.navigation.di.ActivityDestination
import com.guerra.enrico.navigation.di.FragmentDestination

/**
 * Created by enrico
 * on 16/05/2020.
 */
interface Navigator {
  fun openActivity(activity: FragmentActivity, destination: ActivityDestination)

  fun replaceFragment(fragmentManager: FragmentManager, @IdRes containerId: Int, destination: FragmentDestination)
}