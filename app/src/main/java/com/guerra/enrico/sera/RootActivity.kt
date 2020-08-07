package com.guerra.enrico.sera

import android.os.Bundle
import com.guerra.enrico.base_android.extensions.setLightStatusBarIfNeeded
import com.guerra.enrico.base_android.extensions.systemUiFullScreen
import com.guerra.enrico.base_android.arch.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_root.*

/**
 * Created by enrico
 * on 15/12/2019.
 */
@AndroidEntryPoint
class RootActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setLightStatusBarIfNeeded()

    setContentView(R.layout.activity_root)

    rootHost.systemUiFullScreen()
  }

}