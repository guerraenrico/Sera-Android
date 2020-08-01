package com.guerra.enrico.sera.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.guerra.enrico.sera.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * Created by enrico
 * on 01/08/2020.
 */
@AndroidEntryPoint
class MainFragment: Fragment(R.layout.fragment_main) {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupBottomNavigationBar()
  }

  private fun setupBottomNavigationBar() {
    bottomNavigation.setupWithNavController(findNavController())
    bottomNavigation.setOnNavigationItemSelectedListener {
      true
    }
    bottomNavigation.setOnNavigationItemReselectedListener { /* Disable default behavior */ }
  }

}