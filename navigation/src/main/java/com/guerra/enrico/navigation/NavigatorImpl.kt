package com.guerra.enrico.navigation

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
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

internal class NavigatorImpl @Inject constructor() : Navigator {

  override fun startActivity(
    activity: FragmentActivity,
    target: ActivityTarget,
    options: ActivityOptionsCompat?
  ) {
    val clazz = Class.forName(target.className)
    val intent = Intent(Intent.ACTION_VIEW).setClass(activity, clazz)

    val bundle = Bundle()
    if (target is WithInput<*>) {
      bundle.putParcelable(target.key, target.params as Parcelable)
    }
    if (options != null) {
      bundle.putAll(options.toBundle())
    }

    activity.startActivity(intent, bundle)
  }

  override fun startActivityForResult(
    activity: FragmentActivity,
    target: ActivityTarget,
    options: ActivityOptionsCompat?
  ) {
    if (target !is WithResult<*>) {
      throw IllegalArgumentException("Destination ${target.className} doesn't support result")
    }

    val clazz = Class.forName(target.className)
    val intent = Intent(Intent.ACTION_VIEW).setClass(activity, clazz)
    val requestCode = target.code

    val bundle = Bundle()
    if (target is WithInput<*>) {
      bundle.putParcelable(target.key, target.params as Parcelable)
    }
    if (options != null) {
      bundle.putAll(options.toBundle())
    }

    activity.startActivityForResult(intent, requestCode, bundle)
  }

  override fun startActivityForResult(
    fragment: Fragment,
    target: ActivityTarget,
    options: ActivityOptionsCompat?
  ) {
    if (target !is WithResult<*>) {
      throw IllegalArgumentException("Destination ${target.className} doesn't support result")
    }

    val clazz = Class.forName(target.className)
    val intent = Intent(Intent.ACTION_VIEW).setClass(fragment.requireContext(), clazz)
    val requestCode = target.code

    val bundle = Bundle()
    if (target is WithInput<*>) {
      bundle.putParcelable(target.key, target.params as Parcelable)
    }
    if (options != null) {
      bundle.putAll(options.toBundle())
    }

    fragment.startActivityForResult(intent, requestCode, bundle)
  }

  override fun replaceFragment(
    fragmentManager: FragmentManager,
    containerId: Int,
    target: FragmentTarget
  ) {
    val fragment: Fragment = Class.forName(target.className).newInstance() as Fragment

    if (target is WithInput<*>) {
      fragment.arguments = Bundle().apply {
        putParcelable(target.key, target.params as Parcelable)
      }
    }

    fragmentManager.beginTransaction().apply {
      replace(containerId, fragment)
      commit()
    }
  }
}