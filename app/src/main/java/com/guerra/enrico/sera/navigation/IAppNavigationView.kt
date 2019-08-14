package com.guerra.enrico.sera.navigation

import android.app.Activity

/**
 * Created by enrico
 * on 27/05/2018.
 */
interface IAppNavigationView {
  /**
   * Call this when the {@link Activity} is ready to process the NavigationView. Implements
   * general set up of the view.
   *
   * @param activity The activity showing the NavigationView

   * @param self The {@link NavigationItemEnum} of the activity showing the
   *             NavigationView. Pass in {@link NavigationItemEnum#INVALID} if the
   *             activity should not display the NavigationView.
   */
  fun activityReady(activity: Activity, self: NavigationModel.NavigationItemEnum)

  /**
   * Implements UI specific logic to perform initial set up for the NavigationView. This is
   * expected to be called only once.
   */
  fun setUpView()

  /**
   * Call this when some action in the {@link Activity} requires the navigation items to be
   * refreshed (eg user logging in). Implements updating navigation items.
   */
  fun updateNavigationItems()

  /**
   * Implements UI specific logic to display the {@code items}. This is expected to be called each
   * time the navigation items change.
   */
  fun displayNavigationItems(items: Array<NavigationModel.NavigationItemEnum>)

  /**
   * Implements launching the [Activity] linked to the `item`.
   */
  fun itemSelected(item: NavigationModel.NavigationItemEnum)

  /**
   * Implements UI specific logic to display the NavigationView. Note that if the NavigationView
   * should always be visible, this method is empty.
   */
  fun showNavigation()
}