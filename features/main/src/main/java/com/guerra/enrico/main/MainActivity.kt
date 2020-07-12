package com.guerra.enrico.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.guerra.enrico.base.extensions.setLightStatusBarIfNeeded
import com.guerra.enrico.base.extensions.systemUiFullScreen
import com.guerra.enrico.base_android.arch.BaseActivity
import com.guerra.enrico.goals.GoalsNavigationRoutes
import com.guerra.enrico.navigation.Navigator
import com.guerra.enrico.navis_annotation.contracts.FragmentTarget
import com.guerra.enrico.results.ResultsNavigationRoutes
import com.guerra.enrico.settings.SettingsNavigationRoutes
import com.guerra.enrico.todos.TodosNavigationRoutes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * Created by enrico
 * on 15/12/2019.
 */
@AndroidEntryPoint
class MainActivity : BaseActivity() {

  private val viewModel: MainViewModel by viewModels()

  @Inject
  lateinit var navigator: Navigator

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setLightStatusBarIfNeeded()

    setContentView(R.layout.activity_main)

    root.systemUiFullScreen()

    setupBottomNavigationBar()
  }

  private fun setupBottomNavigationBar() {
    viewModel.selectedMenuItemId.observe(this, Observer { itemId ->
      val target = getTarget(itemId)
      navigator.replaceFragment(supportFragmentManager, R.id.main_fragment_host, target)
    })
    bottom_navigation.setOnNavigationItemSelectedListener { item ->
      viewModel.onSelectMenuItem(item.itemId)
      true
    }
    bottom_navigation.setOnNavigationItemReselectedListener { /* Disable default behavior */ }
  }

  private fun getTarget(itemId: Int): FragmentTarget {
    return when (itemId) {
      R.id.navigation_todos -> TodosNavigationRoutes.List.buildTarget()
      R.id.navigation_goals -> GoalsNavigationRoutes.Goals.buildTarget()
      R.id.navigation_results -> ResultsNavigationRoutes.Results.buildTarget()
      R.id.navigation_settings -> SettingsNavigationRoutes.Settings.buildTarget()
      else -> throw IllegalArgumentException("Invalid bottom navigation item id")
    }
  }
}