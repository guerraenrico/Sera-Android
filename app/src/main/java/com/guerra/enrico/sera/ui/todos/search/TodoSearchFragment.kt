package com.guerra.enrico.sera.ui.todos.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.app.ActivityCompat.finishAfterTransition
import androidx.fragment.app.Fragment
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.databinding.FragmentTodosSearchBinding
import com.guerra.enrico.sera.exceptions.MessageExceptionManager

/**
 * Created by enrico
 * on 23/03/2020.
 */
class TodoSearchFragment : Fragment() {

  private lateinit var binding: FragmentTodosSearchBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentTodosSearchBinding.inflate(inflater, container, false)
    binding.lifecycleOwner = viewLifecycleOwner
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
  }

  private fun initView() {
    binding.toolbar.navigationIcon = getDrawable(requireContext(), R.drawable.ic_close)
    binding.toolbar.setNavigationOnClickListener { finishAfterTransition(requireActivity()) }
    val messageResources = MessageExceptionManager(Exception()).getResources()
    binding.messageLayout.apply {
      setImage(messageResources.icon)
      setMessage(messageResources.message)
      setButton(resources.getString(R.string.message_layout_button_try_again)) {}
      show()
    }
  }

}