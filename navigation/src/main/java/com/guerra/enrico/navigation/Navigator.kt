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
  fun startActivityForResult(fragment: Fragment, direction: ActivityDirection, options: Bundle? = null)

  fun replaceFragment(fragmentManager: FragmentManager, @IdRes containerId: Int, direction: FragmentDirection)
}