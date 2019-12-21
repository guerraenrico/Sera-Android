package com.guerra.enrico.sera.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.guerra.enrico.sera.R
import kotlinx.android.synthetic.main.fragment_1.*
import kotlinx.android.synthetic.main.main_activity.*

/**
 * Created by enrico
 * on 15/12/2019.
 */
class MainActivity: AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)

    setupBottomNavigationBar()
  }

  private fun setupBottomNavigationBar() {
    val navController = Navigation.findNavController(this, R.id.mainFragmentHost)
    bottomNavigation.setupWithNavController(navController = navController)
  }
}

class Fragment1: Fragment() {
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_1, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    text.text = this::class.java.simpleName
  }
}

class Fragment2: Fragment() {
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_1, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    text.text = this::class.java.simpleName
  }
}

class Fragment3: Fragment() {
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_1, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    text.text = this::class.java.simpleName
  }
}