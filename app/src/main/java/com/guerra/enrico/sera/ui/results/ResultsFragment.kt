package com.guerra.enrico.sera.ui.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.exceptions.MessageExceptionManager
import com.guerra.enrico.sera.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_results.*
import kotlinx.android.synthetic.main.toolbar.*
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by enrico
 * on 30/07/2019.
 */
class ResultsFragment : BaseFragment() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private val viewModel: ResultsViewModel by viewModels { viewModelFactory }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_results, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    toolbarTitle?.text = resources.getString(R.string.title_results)
    initView()
  }

  private fun initView() {
    val messageResources = MessageExceptionManager(Exception()).getResources()
    message_layout.apply {
      setImage(messageResources.icon)
      setMessage(messageResources.message)
      setButton(resources.getString(R.string.message_layout_button_try_again)) {}
      show()
    }
  }
}