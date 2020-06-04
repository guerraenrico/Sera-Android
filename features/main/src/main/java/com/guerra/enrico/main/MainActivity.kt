package com.guerra.enrico.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.guerra.enrico.base.extensions.setLightStatusBarIfNeeded
import com.guerra.enrico.base.extensions.systemUiFullScreen
import com.guerra.enrico.base_android.arch.BaseActivity
import com.guerra.enrico.main.databinding.ActivityMainBinding
import com.guerra.enrico.navigation.Navigator
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
      val direction = when (item.itemId) {
        // TODO fix
//        R.id.navigation_todos -> TodosDirections.Tasks.Fragment()
//        R.id.navigation_goals -> GoalsDirections.Goals.Fragment()
//        R.id.navigation_results -> ResultsDirections.Results.Fragment()
//        R.id.navigation_settings -> SettingsDirections.Settings.Fragment()
        else -> return@setOnNavigationItemSelectedListener false
      }
      navigator.replaceFragment(supportFragmentManager, R.id.main_fragment_host, direction)
      true
    }
    binding.bottomNavigation.setOnNavigationItemReselectedListener { /* Disable default behavior */ }

    // Show default fragment
    // TODO fix
//    binding.bottomNavigation.selectedItemId = R.id.navigation_todos
//    val direction = TodosDirections.Tasks.Fragment()
//    navigator.replaceFragment(supportFragmentManager, R.id.main_fragment_host, direction)
  }
}