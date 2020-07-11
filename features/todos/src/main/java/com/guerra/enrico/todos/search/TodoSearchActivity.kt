package com.guerra.enrico.todos.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.guerra.enrico.base.extensions.observeEvent
import com.guerra.enrico.base.extensions.setLightStatusBarIfNeeded
import com.guerra.enrico.base_android.arch.BaseActivity
import com.guerra.enrico.todos.R
import com.guerra.enrico.todos.TodosNavigationRoutes
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by enrico
 * on 16/03/2020.
 */
@AndroidEntryPoint
internal class TodoSearchActivity : BaseActivity() {
  private val viewModel: TodoSearchViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setLightStatusBarIfNeeded()

    setContentView(R.layout.activity_todo_search)

    observeResult()
  }

  private fun observeResult() {
    observeEvent(viewModel.searchData) {
      val key = TodosNavigationRoutes.Search.resultKey
      val intent = Intent().apply {
        putExtra(key, it)
      }
      setResult(Activity.RESULT_OK, intent)
      finishAfterTransition()
    }
  }
}