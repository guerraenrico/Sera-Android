package com.guerra.enrico.sera.navigation

import android.app.Activity
import android.content.Intent
import android.os.Handler
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.navigation.AppNavigationViewAbstractImpl.Config.BOTTOM_NAV_ANIM_GRACE

/**
 * Created by enrico
 * on 27/05/2018.
 */
abstract class AppNavigationViewAbstractImpl : IAppNavigationView, NavigationModel() {

  object Config {
    const val BOTTOM_NAV_ANIM_GRACE = 115L
  }

  protected lateinit var mActivity: Activity
  protected lateinit var mSelfItem: NavigationItemEnum
  private val handler = Handler()

  override fun activityReady(activity: Activity, self: NavigationItemEnum) {
    mActivity = activity
    mSelfItem = self
    setUpView()
  }

  abstract override fun setUpView()

  abstract override fun updateNavigationItems()

  abstract override fun displayNavigationItems(items: Array<NavigationItemEnum>)

  override fun itemSelected(item: NavigationItemEnum) {
    if (item.classToLaunch !== null) {
      handler.postDelayed({
        mActivity.startActivity(Intent(mActivity, item.classToLaunch))
        if (item.finishCurrentActivity) {
          mActivity.finish()
          mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
      }, BOTTOM_NAV_ANIM_GRACE)
    }
  }

  abstract override fun showNavigation()
}