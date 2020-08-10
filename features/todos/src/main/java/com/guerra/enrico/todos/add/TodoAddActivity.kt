package com.guerra.enrico.todos.add

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.guerra.enrico.base_android.extensions.setLightStatusBarIfNeeded
import com.guerra.enrico.todos.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class TodoAddActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setLightStatusBarIfNeeded()
    setContentView(R.layout.activity_todo_add)
  }

}