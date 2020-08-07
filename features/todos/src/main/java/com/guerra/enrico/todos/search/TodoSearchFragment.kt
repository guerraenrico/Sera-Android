package com.guerra.enrico.todos.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.app.ActivityCompat.finishAfterTransition
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.guerra.enrico.base.Result
import com.guerra.enrico.base_android.extensions.applyWindowInsets
import com.guerra.enrico.base_android.extensions.observe
import com.guerra.enrico.base_android.arch.BaseFragment
import com.guerra.enrico.base_android.exception.MessageExceptionManager
import com.guerra.enrico.todos.R
import com.guerra.enrico.todos.adapter.SuggestionAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_todos_search.*

/**
 * Created by enrico
 * on 23/03/2020.
 */
@AndroidEntryPoint
internal class TodoSearchFragment : BaseFragment() {
  private val viewModel: TodoSearchViewModel by activityViewModels()

  private lateinit var suggestionAdapter: SuggestionAdapter

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_todos_search, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
  }

  private fun initView() {
    toolbar.apply {
      navigationIcon = getDrawable(requireContext(), R.drawable.ic_close)
      setNavigationOnClickListener { finishAfterTransition(requireActivity()) }
      setOnMenuItemClickListener { onMenuItemClick(it) }
      applyWindowInsets(top = true)
    }

    setupRecyclerView()
    observeSuggestionList()
  }

  private fun observeSuggestionList() = observe(viewModel.suggestionsResult) { suggestionsResult ->
    when (suggestionsResult) {
      is Result.Loading -> {
      }
      is Result.Success -> {
        message_layout.hide()
        recycler_view_suggestions.isVisible = true
        if (suggestionsResult.data.isEmpty()) {
          recycler_view_suggestions.isVisible = false
          message_layout.apply {
            setMessage("No suggestions")
            setButton(resources.getString(R.string.message_layout_button_try_again)) {
              viewModel.loadWhileTyping("")
            }
            show()
          }
        } else {
          suggestionAdapter.submitList(suggestionsResult.data)
        }
      }
      is Result.Error -> {
        val messageResources = MessageExceptionManager(Exception()).getResources()
        recycler_view_suggestions.isVisible = false
        message_layout.apply {
          setImage(messageResources.icon)
          setMessage(messageResources.message)
          setButton(resources.getString(R.string.message_layout_button_try_again)) {
            viewModel.loadWhileTyping("")
          }
          show()
        }
      }
    }
  }

  private fun setupRecyclerView() {
    suggestionAdapter = SuggestionAdapter {
      viewModel.onSuggestionClick(it)
    }
    recycler_view_suggestions.apply {
      adapter = suggestionAdapter
    }
  }

  private fun onMenuItemClick(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_search_todo -> {
        val text = search_toolbar_edit_text_search.text.toString()
        viewModel.onSearch(text)
        true
      }
      else -> false
    }
  }

  companion object {
    fun newInstance() = TodoSearchFragment()
  }
}