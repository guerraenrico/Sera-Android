package com.guerra.enrico.sera.ui.goals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.guerra.enrico.base.util.viewModelProvider
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.exceptions.MessageExceptionManager
import com.guerra.enrico.sera.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_goals.*
import kotlinx.android.synthetic.main.toolbar.*
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by enrico
 * on 27/05/2018.
 */
class GoalsFragment : BaseFragment() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private lateinit var viewModel: GoalsViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_goals, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = viewModelProvider(viewModelFactory)

    toolbarTitle?.text = resources.getString(R.string.title_goals)
    initView()
  }

  private fun initView() {
    val messageResources = MessageExceptionManager(Exception()).getResources()
    messageLayout.apply {
      setImage(messageResources.icon)
      setMessage(messageResources.message)
      setButton(resources.getString(R.string.message_layout_button_try_again)) {}
      show()
    }
  }
}