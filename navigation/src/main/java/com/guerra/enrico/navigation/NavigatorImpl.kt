package com.guerra.enrico.navigation

import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.guerra.enrico.navis_annotation.contracts.ActivityTarget
import com.guerra.enrico.navis_annotation.contracts.FragmentTarget
import com.guerra.enrico.navis_annotation.contracts.WithInput
import com.guerra.enrico.navis_annotation.contracts.WithResult
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
    target: ActivityTarget,
    options: ActivityOptionsCompat?
  ) {

    val clazz = Class.forName(target.className)
    val intent = Intent(Intent.ACTION_VIEW).setClass(activity, clazz)

    val bundle = Bundle(options?.toBundle())
    if (target is WithInput<*>) {
//      bundle.putParcelable(target.key, target.params)
    }


//    navigationController.startActivity(activity, intent, bundle)
  }

  override fun startActivityForResult(
    activity: FragmentActivity,
    target: ActivityTarget,
    options: ActivityOptionsCompat?
  ) {
    val clazz = Class.forName(target.className)
    val intent = Intent(Intent.ACTION_VIEW).setClass(activity, clazz)
  }

  override fun startActivityForResult(
    fragment: Fragment,
    target: ActivityTarget,
    options: ActivityOptionsCompat?
  ) {

    val clazz = Class.forName(target.className)
    val intent = Intent(Intent.ACTION_VIEW).setClass(fragment.requireContext(), clazz)

    if (target !is WithResult<*>) {
      throw IllegalArgumentException("Destination ${target.className} doesn't support result")
    }

//    navigationController.startActivityForResult(
//      fragment,
//      intent,
//      options
//    )
  }

  override fun replaceFragment(
    fragmentManager: FragmentManager,
    containerId: Int,
    target: FragmentTarget
  ) {
    val fragment: Fragment = Class.forName(target.className).newInstance() as Fragment

    if (target is WithInput<*>) {
      // TODO handle pass parameter to fragment
    }
    navigationController.replaceFragment(fragmentManager, containerId, fragment)
  }
}