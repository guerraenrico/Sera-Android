package com.guerra.enrico.sera.ui.base

import android.os.Bundle
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.navigation.AppNavigationViewAsBottomNavImpl
import com.guerra.enrico.sera.navigation.IAppNavigationView
import com.guerra.enrico.sera.navigation.NavigationModel
import com.guerra.enrico.sera.widget.OverlayLoader
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.bottom_navigation_view.*

/**
 * Created by enrico
 * on 27/05/2018.
 */
abstract class BaseActivity : DaggerAppCompatActivity() {
    lateinit var appNavigationView: IAppNavigationView
    private lateinit var overlayLoader: OverlayLoader

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        if (navigation !== null) {
            appNavigationView = AppNavigationViewAsBottomNavImpl(navigation)
            appNavigationView.activityReady(this, getSelfNavDrawerItem())
        }
        overlayLoader = OverlayLoader.make(this, resources.getString(R.string.label_loading))
    }

    fun showSnakbar(@StringRes messageId: Int) {
        if (isFinishing) return
        showSnakbar(resources.getString(messageId))
    }

    fun showSnakbar(message: String) {
        if (isFinishing) return
        Snackbar.make( findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
    }

    fun showOverlayLoader() {
        if (isFinishing) return
        overlayLoader.show()
    }

    fun hideOverlayLoader() {
        if (isFinishing) return
        overlayLoader.hide()
    }

    abstract fun initView()

    /**
     * Returns the navigation drawer item that corresponds to this Activity. Subclasses of
     * BaseActivity override this to indicate what nav drawer item corresponds to them Return
     * NAVDRAWER_ITEM_INVALID to mean that this Activity should not have a Nav Drawer.
     */
    protected open fun getSelfNavDrawerItem(): NavigationModel.NavigationItemEnum {
        return NavigationModel.NavigationItemEnum.INVALID
    }

}