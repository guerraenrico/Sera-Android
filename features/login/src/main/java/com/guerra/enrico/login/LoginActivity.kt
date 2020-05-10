package com.guerra.enrico.login

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.guerra.enrico.base.extensions.setLightStatusBarIfNeeded
import com.guerra.enrico.base.extensions.systemUiFullScreen
import com.guerra.enrico.base_android.arch.BaseActivity
import com.guerra.enrico.login.databinding.ActivityLoginBinding

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