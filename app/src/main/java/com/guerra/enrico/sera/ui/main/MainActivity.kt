package com.guerra.enrico.sera.ui.main

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.transition.MaterialContainerTransformSharedElementCallback
import com.guerra.enrico.base.extensions.setLightStatusBarIfNeeded
import com.guerra.enrico.base.extensions.systemUiFullScreen
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.databinding.ActivityMainBinding

/**
 * Created by enrico
 * on 15/12/2019.
 */
class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {

    window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
    setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())

    super.onCreate(savedInstanceState)
    setLightStatusBarIfNeeded()

    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    binding.lifecycleOwner = this

    binding.root.systemUiFullScreen()

    setupBottomNavigationBar()
  }

  private fun setupBottomNavigationBar() {
    val navController = Navigation.findNavController(this, R.id.main_fragment_host)
    binding.bottomNavigation.setupWithNavController(navController = navController)
    binding.bottomNavigation.setOnNavigationItemReselectedListener { /* Disable default behavior */ }
  }
}