package com.guerra.enrico.todos.add

import android.os.Bundle
import com.guerra.enrico.base_android.arch.BaseActivity
import com.guerra.enrico.todos.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class TodoAddActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_todo_add)
  }

}