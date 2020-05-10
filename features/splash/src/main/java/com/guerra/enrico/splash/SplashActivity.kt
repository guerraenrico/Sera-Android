package com.guerra.enrico.splash

import android.os.Bundle
import com.guerra.enrico.base_android.arch.BaseActivity

/**
 * Created by enrico
 * on 17/10/2018.
 */
class SplashActivity : BaseActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)
  }
}