package com.guerra.enrico.sera.ui.login

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.guerra.enrico.base.extensions.setLightStatusBarIfNeeded
import com.guerra.enrico.base.extensions.systemUiFullScreen
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.databinding.ActivityLoginBinding
import com.guerra.enrico.sera.ui.base.BaseActivity

/**
 * Created by enrico
 * on 12/10/2018.
 */

class LoginActivity : BaseActivity() {
  private lateinit var binding: ActivityLoginBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setLightStatusBarIfNeeded()

    binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
    binding.root.systemUiFullScreen()
  }
}