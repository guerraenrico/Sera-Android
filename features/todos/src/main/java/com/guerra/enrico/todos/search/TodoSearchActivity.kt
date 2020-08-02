package com.guerra.enrico.todos.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.guerra.enrico.base.extensions.observeEvent
import com.guerra.enrico.base.extensions.setLightStatusBarIfNeeded
import com.guerra.enrico.base_android.arch.BaseActivity
import com.guerra.enrico.todos.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by enrico
 * on 16/03/2020.
 */
@AndroidEntryPoint
internal class TodoSearchActivity : BaseActivity() {
  private val viewModel: TodoSearchViewModel by viewModels()

  companion object {
    const val SEARCH_RESULT_KEY = "search_result_key"
    const val SEARCH_RESULT_REQUEST_CODE = 1001

  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setLightStatusBarIfNeeded()

    setContentView(R.layout.activity_todo_search)

    observeResult()
  }

  private fun observeResult() {
    observeEvent(viewModel.searchData) { searchData ->
      val intent = Intent().apply {
        putExtra(SEARCH_RESULT_KEY, searchData)
      }
      setResult(Activity.RESULT_OK, intent)
      finishAfterTransition()
    }
  }
}