package com.guerra.enrico.todos.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.guerra.enrico.base.extensions.observeEvent
import com.guerra.enrico.base.extensions.setLightStatusBarIfNeeded
import com.guerra.enrico.base.extensions.systemUiFullScreen
import com.guerra.enrico.base_android.arch.BaseActivity
import com.guerra.enrico.navigation.TODO_SEARCH_OUTPUT_KEY
import com.guerra.enrico.todos.R
import com.guerra.enrico.todos.databinding.ActivityTodoSearchBinding
import javax.inject.Inject

/**
 * Created by enrico
 * on 16/03/2020.
 */
internal class TodoSearchActivity : BaseActivity() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private val viewModel: TodoSearchViewModel by viewModels { viewModelFactory }

  private lateinit var binding: ActivityTodoSearchBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setLightStatusBarIfNeeded()

    binding = DataBindingUtil.setContentView(this, R.layout.activity_todo_search)
    binding.lifecycleOwner = this
    binding.root.systemUiFullScreen()

    observeResult()
  }

  private fun observeResult() {
    observeEvent(viewModel.searchData) {
      val intent = Intent().apply {
        putExtra(TODO_SEARCH_OUTPUT_KEY, it)
      }
      setResult(Activity.RESULT_OK, intent)
      finishAfterTransition()
    }
  }
}