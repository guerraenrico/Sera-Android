package com.guerra.enrico.sera.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.guerra.enrico.base.extensions.setLightStatusBarIfNeeded
import com.guerra.enrico.base.extensions.systemUiFullScreen
import com.guerra.enrico.base_android.arch.BaseActivity
import com.guerra.enrico.sera.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by enrico
 * on 15/12/2019.
 */
@AndroidEntryPoint
class MainActivity : BaseActivity() {

  private val viewModel: MainViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setLightStatusBarIfNeeded()

    setContentView(R.layout.activity_main)

    root.systemUiFullScreen()

    setupBottomNavigationBar()
  }

  private fun setupBottomNavigationBar() {
    bottomNavigation.setupWithNavController(findNavController(R.id.mainFragmentHost))
    bottomNavigation.setOnNavigationItemSelectedListener { item ->
      viewModel.onSelectMenuItem(item.itemId)
      true
    }
    bottomNavigation.setOnNavigationItemReselectedListener { /* Disable default behavior */ }
  }
}