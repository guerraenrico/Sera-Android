package com.guerra.enrico.todos.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.app.ActivityCompat.finishAfterTransition
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.guerra.enrico.base.extensions.exhaustive
import com.guerra.enrico.base.extensions.lazyFast
import com.guerra.enrico.base_android.arch.BaseFragment
import com.guerra.enrico.base_android.extensions.applyWindowInsets
import com.guerra.enrico.models.todos.Suggestion
import com.guerra.enrico.navigation.models.todos.SearchData
import com.guerra.enrico.todos.R
import com.guerra.enrico.todos.adapter.SuggestionAdapter
import com.guerra.enrico.todos.search.models.TodoSearchEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_todos_search.*

@AndroidEntryPoint
internal class TodoSearchFragment : BaseFragment() {
  private val viewModel: TodoSearchViewModel by viewModels()

  private val suggestionAdapter: SuggestionAdapter by lazyFast {
    SuggestionAdapter {
      viewModel.onSuggestionClick(it)
    }
  }

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
    setupObservers()
  }

  private fun setupRecyclerView() {
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

  private fun setupObservers() {
    observe(viewModel.viewState) { state ->
      val suggestions = state.suggestions
      if (suggestions.isEmpty()) {
        bindEmptySuggestions()
      } else {
        bindSuggestions(suggestions)
      }
    }

    observeEvent(viewModel.events) { event ->
      when (event) {
        is TodoSearchEvent.SearchResult -> sendResult(event.data)
      }.exhaustive
    }
  }

  private fun bindEmptySuggestions() {
    recycler_view_suggestions.isVisible = false
    messageLayout.apply {
      setMessage("No suggestions")
      setButton(resources.getString(R.string.message_layout_button_try_again)) {
        viewModel.loadWhileTyping("")
      }
      show()
    }
  }

  private fun bindSuggestions(suggestions: List<Suggestion>) {
    messageLayout.hide()
    recycler_view_suggestions.isVisible = true
    suggestionAdapter.submitList(suggestions)
  }

  private fun sendResult(searchData: SearchData) = with(requireActivity()) {
    val intent = Intent().apply {
      putExtra(SEARCH_RESULT_KEY, searchData)
    }
    setResult(Activity.RESULT_OK, intent)
    finishAfterTransition()
  }

  companion object {
    const val SEARCH_RESULT_KEY = "search_result_key"
    const val SEARCH_RESULT_REQUEST_CODE = 1001
  }

}