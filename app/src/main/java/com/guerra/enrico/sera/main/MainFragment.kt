package com.guerra.enrico.sera.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.guerra.enrico.sera.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val nestedHostFragment: NavHostFragment =
      childFragmentManager.findFragmentById(R.id.mainFragmentHost) as NavHostFragment
    val navController = nestedHostFragment.navController
    setupBottomNavigationBar(navController)
  }

  private fun setupBottomNavigationBar(navController: NavController) {
    bottomNavigation.setupWithNavController(navController)
    bottomNavigation.setOnNavigationItemReselectedListener { /* Disable default behavior */ }
  }

}