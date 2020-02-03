package com.guerra.enrico.sera.ui.splash

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.guerra.enrico.base.extensions.viewModelProvider
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.ui.base.BaseActivity
import javax.inject.Inject

/**
 * Created by enrico
 * on 17/10/2018.
 */
class SplashActivity : BaseActivity() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private lateinit var viewModel: SplashViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)
    viewModel = viewModelProvider(viewModelFactory)
  }
}