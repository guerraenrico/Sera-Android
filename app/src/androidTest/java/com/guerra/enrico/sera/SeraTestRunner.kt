package com.guerra.enrico.sera

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

/**
 * Created by enrico
 * on 05/07/2020.
 */
class SeraTestRunner : AndroidJUnitRunner() {
  override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
    return super.newApplication(cl, HiltTestApplication::class.java.name, context)
  }
}