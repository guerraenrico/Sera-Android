package com.guerra.enrico.base_android.utils

import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.guerra.enrico.base_android.R

/**
 * Created by enrico
 * on 28/05/2020.
 */
class SingleFragmentActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val content = FragmentContainerView(this).apply {
      layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
      id = R.id.container
    }
    setContentView(content)
  }

  fun setFragment(fragment: Fragment) {
    supportFragmentManager.beginTransaction()
      .add(R.id.content, fragment, "TEST")
      .commit()
  }

  fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {
    supportFragmentManager.beginTransaction()
      .replace(R.id.content, fragment)
      .apply {
        if (addToBackStack) {
          addToBackStack(null)
        }
      }
      .commit()
  }
}