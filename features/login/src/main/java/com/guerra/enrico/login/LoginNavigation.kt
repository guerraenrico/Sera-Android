package com.guerra.enrico.login

import com.guerra.enrico.navis_annotation.annotations.ActivityRoute
import com.guerra.enrico.navis_annotation.annotations.FragmentRoute
import com.guerra.enrico.navis_annotation.annotations.Routes

/**
 * Created by enrico
 * on 02/06/2020.
 */
@Routes
abstract class LoginNavigation {
  @ActivityRoute(LoginActivity::class)
  object Login

  @FragmentRoute(LoginFragment::class)
  object Form

  @FragmentRoute(SyncFragment::class)
  object Sync
}