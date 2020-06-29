package com.guerra.enrico.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.guerra.enrico.base.extensions.setLightStatusBarIfNeeded
import com.guerra.enrico.base.extensions.systemUiFullScreen
import com.guerra.enrico.base_android.arch.BaseActivity
import com.guerra.enrico.goals.GoalsNavigationRoutes
import com.guerra.enrico.main.databinding.ActivityMainBinding
import com.guerra.enrico.navigation.Navigator
import com.guerra.enrico.navis_annotation.contracts.FragmentTarget
import com.guerra.enrico.results.ResultsNavigationRoutes
import com.guerra.enrico.settings.SettingsNavigationRoutes
import com.guerra.enrico.todos.TodosNavigationRoutes
import java.lang.IllegalArgumentException
import javax.inject.Inject

/**
 * Created by enrico
 * on 15/12/2019.
 */
class MainActivity : BaseActivity() {
  private lateinit var binding: ActivityMainBinding

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private val viewModel: MainViewModel by viewModels { viewModelFactory }

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
    viewModel.selectedMenuItemId.observe(this, Observer { itemId ->
      val target = getTarget(itemId)
      navigator.replaceFragment(supportFragmentManager, R.id.main_fragment_host, target)
    })
    binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
      viewModel.onSelectMenuItem(item.itemId)
      true
    }
    binding.bottomNavigation.setOnNavigationItemReselectedListener { /* Disable default behavior */ }
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