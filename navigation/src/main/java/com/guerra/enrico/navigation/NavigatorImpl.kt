package com.guerra.enrico.navigation

import android.content.Context
import android.net.Uri
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by enrico
 * on 16/05/2020.
 */
internal class NavigatorImpl @Inject constructor(
  @ApplicationContext private val context: Context
) : Navigator {

  override fun getUriLogin(): Uri {
    return context.getDeepLink(R.string.login_deep_link)
  }

  override fun getUriMain(): Uri {
    return context.getDeepLink(R.string.main_deep_link)
  }

  override fun getUriTodos(): Uri {
    return context.getDeepLink(R.string.todos_deep_link)
  }

  override fun getUriGoals(): Uri {
    return context.getDeepLink(R.string.goals_deep_link)
  }

  override fun getUriResults(): Uri {
    return context.getDeepLink(R.string.results_deep_link)
  }

  override fun getUriSettings(): Uri {
    return context.getDeepLink(R.string.settings_deep_link)
  }

  private fun Context.getDeepLink(@StringRes deepLinkId: Int): Uri {
    val deepLink = getString(deepLinkId)
    return Uri.parse(deepLink)
  }
}