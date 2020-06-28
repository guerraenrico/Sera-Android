package com.guerra.enrico.navigation

import androidx.annotation.IdRes
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.guerra.enrico.navis_annotation.contracts.ActivityTarget
import com.guerra.enrico.navis_annotation.contracts.FragmentTarget

/**
 * Created by enrico
 * on 16/05/2020.
 */
interface Navigator {
  fun startActivity(
    activity: FragmentActivity,
    target: ActivityTarget,
    options: ActivityOptionsCompat? = null
  )

  fun startActivityForResult(
    activity: FragmentActivity,
    target: ActivityTarget,
    options: ActivityOptionsCompat? = null
  )

  fun startActivityForResult(
    fragment: Fragment,
    target: ActivityTarget,
    options: ActivityOptionsCompat? = null
  )

  fun replaceFragment(
    fragmentManager: FragmentManager,
    @IdRes containerId: Int,
    target: FragmentTarget
  )
}