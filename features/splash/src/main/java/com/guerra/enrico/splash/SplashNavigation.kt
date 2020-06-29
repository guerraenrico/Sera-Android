package com.guerra.enrico.splash

import com.guerra.enrico.navis_annotation.annotations.ActivityRoute
import com.guerra.enrico.navis_annotation.annotations.Routes

/**
 * Created by enrico
 * on 02/06/2020.
 */
@Routes
abstract class SplashNavigation {
  @ActivityRoute(SplashActivity::class)
  object Splash
}