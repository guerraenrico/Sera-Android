package com.guerra.enrico.sera

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.guerra.enrico.base_android.extensions.setLightStatusBarIfNeeded
import com.guerra.enrico.base_android.extensions.systemUiFullScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_root.*

@AndroidEntryPoint
class RootActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setLightStatusBarIfNeeded()

    setContentView(R.layout.activity_root)

    rootHost.systemUiFullScreen()
  }

}