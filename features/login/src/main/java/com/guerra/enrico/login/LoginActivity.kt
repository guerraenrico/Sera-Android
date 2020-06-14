package com.guerra.enrico.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.guerra.enrico.base.extensions.observeEvent
import com.guerra.enrico.base.extensions.setLightStatusBarIfNeeded
import com.guerra.enrico.base.extensions.systemUiFullScreen
import com.guerra.enrico.base_android.arch.BaseActivity
import com.guerra.enrico.login.databinding.ActivityLoginBinding
import com.guerra.enrico.login.models.Step
import com.guerra.enrico.navigation.Navigator
import javax.inject.Inject

/**
 * Created by enrico
 * on 12/10/2018.
 */

internal class LoginActivity : BaseActivity() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private val viewModel: LoginViewModel by viewModels { viewModelFactory }

  @Inject
  lateinit var navigator: Navigator

  private lateinit var binding: ActivityLoginBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setLightStatusBarIfNeeded()

    binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
    binding.root.systemUiFullScreen()

    observeEvent(viewModel.step) {
      when (it) {
        Step.LOGIN -> findNavController(R.id.loginFragmentHost).navigate(R.id.google_login)
        Step.SYNC -> findNavController(R.id.loginFragmentHost).navigate(R.id.login_sync)
      }
    }
  }
}