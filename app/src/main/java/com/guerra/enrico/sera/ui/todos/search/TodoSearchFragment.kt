package com.guerra.enrico.sera.ui.todos.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.app.ActivityCompat.finishAfterTransition
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.guerra.enrico.base.Result
import com.guerra.enrico.base.extensions.observe
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.data.exceptions.MessageExceptionManager
import com.guerra.enrico.sera.databinding.FragmentTodosSearchBinding
import com.guerra.enrico.sera.ui.todos.adapter.SuggestionAdapter
import javax.inject.Inject

/**
 * Created by enrico
 * on 23/03/2020.
 */
class TodoSearchFragment : Fragment() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private val viewModel: TodoSearchViewModel by activityViewModels { viewModelFactory }

  private lateinit var binding: FragmentTodosSearchBinding
  private lateinit var suggestionAdapter: SuggestionAdapter

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentTodosSearchBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
  }

  private fun initView() {
    binding.toolbar.apply {
      navigationIcon = getDrawable(requireContext(), R.drawable.ic_close)
      setNavigationOnClickListener { finishAfterTransition(requireActivity()) }

      setOnMenuItemClickListener { onMenuItemClick(it) }
      setupRecyclerView()
      observeSuggestionList()
    }

  }

  private fun observeSuggestionList() {
    observe(viewModel.suggestionsResult) { suggestionsResult ->
      when (suggestionsResult) {
        is Result.Loading -> {
        }
        is Result.Success -> {
          binding.messageLayout.hide()
          binding.recyclerViewSuggestions.isVisible = true
          // TODO Show custom message if no item found
          suggestionAdapter.submitList(suggestionsResult.data)
        }
        is Result.Error -> {
          val messageResources = MessageExceptionManager(Exception()).getResources()
          binding.recyclerViewSuggestions.isVisible = false
          binding.messageLayout.apply {
            setImage(messageResources.icon)
            setMessage(messageResources.message)
            setButton(resources.getString(R.string.message_layout_button_try_again)) {}
            show()
          }
        }
      }
    }
  }

  private fun setupRecyclerView() {
    suggestionAdapter = SuggestionAdapter()
    binding.recyclerViewSuggestions.apply {
      adapter = suggestionAdapter
    }
  }

  private fun onMenuItemClick(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_search_todo -> true
      else -> false
    }
  }

}