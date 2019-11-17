package com.guerra.enrico.sera.navigation

import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Created by enrico
 * on 27/05/2018.
 */
class AppNavigationViewAsBottomNavImpl(val navigationView: BottomNavigationView) : AppNavigationViewAbstractImpl(), BottomNavigationView.OnNavigationItemSelectedListener {
  override fun setUpView() {
    navigationView.setOnNavigationItemSelectedListener(this)
    displayNavigationItems(NavigationModel().items)

  }

  override fun updateNavigationItems() {
  }

  override fun displayNavigationItems(items: Array<NavigationItemEnum>) {
    val menu = navigationView.menu
    for (item in items) {
      val menuItem = menu.findItem(item.id)
      if (menuItem !== null) {
        menuItem.isVisible = true
        menuItem.setIcon(item.iconResource)
        menuItem.setTitle(item.titleResources)
        if (item === mSelfItem) {
          menuItem.isChecked = true
        }

      }
    }
  }

  override fun showNavigation() {

  }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    val navItem = NavigationItemEnum.Static.getById(item.itemId)
    if (navItem !== mSelfItem) {
      itemSelected(navItem)
      return true
    }
    return false
  }
}