package com.guerra.enrico.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.guerra.enrico.base.extensions.setLightStatusBarIfNeeded
import com.guerra.enrico.base.extensions.systemUiFullScreen
import com.guerra.enrico.base_android.arch.BaseActivity
import com.guerra.enrico.main.databinding.ActivityMainBinding
import com.guerra.enrico.navigation.Navigator
import com.guerra.enrico.navigation.di.FragmentDestination
import javax.inject.Inject

/**
 * Created by enrico
 * on 15/12/2019.
 */
class MainActivity : BaseActivity() {
  private lateinit var binding: ActivityMainBinding

  @Inject
  lateinit var navigator: Navigator

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setLightStatusBarIfNeeded()

    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    binding.lifecycleOwner = this

    binding.root.systemUiFullScreen()

    setupBottomNavigationBar()
  }

  private fun setupBottomNavigationBar() {
    binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
      return@setOnNavigationItemSelectedListener when (item.itemId) {
        R.id.navigation_todos -> {
          navigator.replaceFragment(
            supportFragmentManager, R.id.main_fragment_host, FragmentDestination.TODOS
          )
          true
        }
        R.id.navigation_goals -> {
          navigator.replaceFragment(
            supportFragmentManager, R.id.main_fragment_host, FragmentDestination.GOALS
          )
          true
        }
        R.id.navigation_results -> {
          navigator.replaceFragment(
            supportFragmentManager, R.id.main_fragment_host, FragmentDestination.RESULTS
          )
          true
        }
        R.id.navigation_settings -> {
          navigator.replaceFragment(
            supportFragmentManager, R.id.main_fragment_host, FragmentDestination.SETTINGS
          )
          true
        }
        else -> false
      }
    }
    binding.bottomNavigation.setOnNavigationItemReselectedListener { /* Disable default behavior */ }
    binding.bottomNavigation.selectedItemId = R.id.navigation_todos
  }
}