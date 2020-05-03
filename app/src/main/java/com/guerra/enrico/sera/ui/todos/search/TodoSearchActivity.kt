package com.guerra.enrico.sera.ui.todos.search

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.guerra.enrico.base.extensions.setLightStatusBarIfNeeded
import com.guerra.enrico.base.extensions.systemUiFullScreen
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.databinding.ActivityTodoSearchBinding
import com.guerra.enrico.sera.ui.base.BaseActivity
import javax.inject.Inject

/**
 * Created by enrico
 * on 16/03/2020.
 */
class TodoSearchActivity : BaseActivity() {
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
  }
}