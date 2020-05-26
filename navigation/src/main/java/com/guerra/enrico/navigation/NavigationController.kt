package com.guerra.enrico.navigation

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.IdRes
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

/**
 * Created by enrico
 * on 17/05/2020.
 */
internal interface NavigationController {
  fun startActivity(
    activity: FragmentActivity,
    intent: Intent,
    options: ActivityOptionsCompat? = null
  )

  fun <I: Any, O: Any> startActivityForResult(
    activity: FragmentActivity,
    input: I,
    contract: ActivityResultContract<I, O>,
    options: ActivityOptionsCompat? = null
  )

  fun <I: Any, O: Any> startActivityForResult(
    fragment: Fragment,
    input: I,
    contract: ActivityResultContract<I, O>,
    options: ActivityOptionsCompat? = null
  )

  fun replaceFragment(
    fragmentManager: FragmentManager,
    @IdRes containerId: Int,
    fragment: Fragment
  )
}