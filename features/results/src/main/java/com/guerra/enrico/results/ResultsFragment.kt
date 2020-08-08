package com.guerra.enrico.results

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.transition.MaterialFadeThrough
import com.guerra.enrico.base_android.arch.BaseFragment
import com.guerra.enrico.base_android.exception.MessageExceptionManager
import com.guerra.enrico.base_android.extensions.applyWindowInsets
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_results.*

@AndroidEntryPoint
internal class ResultsFragment : BaseFragment(R.layout.fragment_results) {
  private val viewModel: ResultsViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enterTransition = MaterialFadeThrough()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
  }

  private fun initView() {
    toolbar_title.text = resources.getString(R.string.title_results)
    toolbar.applyWindowInsets(top = true)

    val messageResources = MessageExceptionManager(Exception()).getResources()
    message_layout.apply {
      setImage(messageResources.icon)
      setMessage(messageResources.message)
      setButton(resources.getString(R.string.message_layout_button_try_again)) {}
      show()
    }
  }
}