package com.guerra.enrico.sera.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.ui.base.BaseActivity
import kotlinx.android.synthetic.main.main_activity.*

/**
 * Created by enrico
 * on 15/12/2019.
 */
class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)
    setupBottomNavigationBar()
  }

  private fun setupBottomNavigationBar() {
    val navController = Navigation.findNavController(this, R.id.mainFragmentHost)
    bottomNavigation.setupWithNavController(navController = navController)
    bottomNavigation.setOnNavigationItemReselectedListener { /* Disable default behavior */ }
  }
}