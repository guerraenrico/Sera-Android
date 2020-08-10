package com.guerra.enrico.todos.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.guerra.enrico.base_android.extensions.setLightStatusBarIfNeeded
import com.guerra.enrico.todos.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class TodoSearchActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setLightStatusBarIfNeeded()
    setContentView(R.layout.activity_todo_search)
  }

}