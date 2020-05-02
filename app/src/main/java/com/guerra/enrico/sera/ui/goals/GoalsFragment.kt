package com.guerra.enrico.sera.ui.goals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.transition.MaterialFadeThrough
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.databinding.FragmentGoalsBinding
import com.guerra.enrico.sera.data.exceptions.MessageExceptionManager
import com.guerra.enrico.sera.ui.base.BaseFragment
import javax.inject.Inject

/**
 * Created by enrico
 * on 27/05/2018.
 */
class GoalsFragment : BaseFragment() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private val viewModel: GoalsViewModel by viewModels { viewModelFactory }

  private lateinit var binding: FragmentGoalsBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentGoalsBinding.inflate(inflater, container, false).apply {
      lifecycleOwner = viewLifecycleOwner
    }
    return binding.root
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enterTransition = MaterialFadeThrough.create()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
  }

  private fun initView() {
    binding.toolbar.toolbarTitle.text = resources.getString(R.string.title_goals)
    val messageResources = MessageExceptionManager(Exception()).getResources()
    binding.messageLayout.apply {
      setImage(messageResources.icon)
      setMessage(messageResources.message)
      setButton(resources.getString(R.string.message_layout_button_try_again)) {}
      show()
    }
  }
}