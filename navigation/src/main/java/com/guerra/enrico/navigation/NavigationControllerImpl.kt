package com.guerra.enrico.navigation

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.IdRes
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import javax.inject.Inject

/**
 * Created by enrico
 * on 17/05/2020.
 */
internal class NavigationControllerImpl @Inject constructor(
) : NavigationController {

  override fun startActivity(
    activity: FragmentActivity,
    intent: Intent,
    options: ActivityOptionsCompat?
  ) {
    activity.startActivity(intent, options?.toBundle())
  }

  override fun <I : Any, O : Any> startActivityForResult(
    activity: FragmentActivity,
    input: I,
    contract: ActivityResultContract<I, O>,
    options: ActivityOptionsCompat?
  ) {

    val startForResult = activity.registerForActivityResult(contract) {

    }
    startForResult.launch(input, options)
  }

  override fun <I : Any, O : Any> startActivityForResult(
    fragment: Fragment,
    input: I,
    contract: ActivityResultContract<I, O>,
    options: ActivityOptionsCompat?
  ) {

    val startForResult = fragment.registerForActivityResult(contract) {

    }
    startForResult.launch(input, options)
  }

  override fun replaceFragment(
    fragmentManager: FragmentManager,
    @IdRes containerId: Int,
    fragment: Fragment
  ) {
    fragmentManager.beginTransaction().apply {
      replace(containerId, fragment)
      commit()
    }
  }
}