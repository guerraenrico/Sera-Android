package com.guerra.enrico.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.guerra.enrico.navigation.di.ActivityDestination
import com.guerra.enrico.navigation.di.FragmentDestination

/**
 * Created by enrico
 * on 17/05/2020.
 */
internal interface NavigationController {
  fun startActivity(activity: FragmentActivity, destination: ActivityDestination, options: Bundle? = null)

  fun startActivityForResult(activity: FragmentActivity, destination: ActivityDestination, requestCode: Int, options: Bundle? = null)
  fun startActivityForResult(fragment: Fragment, destination: ActivityDestination, requestCode: Int, options: Bundle? = null)

  fun replaceFragment(fragmentManager: FragmentManager, @IdRes containerId: Int, destination: FragmentDestination)
}