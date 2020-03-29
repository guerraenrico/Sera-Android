package com.guerra.enrico.sera.ui.todos.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.guerra.enrico.base.extensions.setLightStatusBarIfNeeded
import com.guerra.enrico.base.extensions.systemUiFullScreen
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.databinding.ActivityTodoSearchBinding

/**
 * Created by enrico
 * on 16/03/2020.
 */
class TodoSearchActivity : AppCompatActivity() {
  private lateinit var binding: ActivityTodoSearchBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setLightStatusBarIfNeeded()

    binding = DataBindingUtil.setContentView(this, R.layout.activity_todo_search)
    binding.lifecycleOwner = this
    binding.root.systemUiFullScreen()
  }
}