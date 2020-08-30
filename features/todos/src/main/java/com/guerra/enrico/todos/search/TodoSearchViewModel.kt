package com.guerra.enrico.todos.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.guerra.enrico.base.Result
import com.guerra.enrico.base.coroutine.AutoDisposableJob
import com.guerra.enrico.base.dispatcher.CPUDispatcher
import com.guerra.enrico.base.extensions.event
import com.guerra.enrico.base_android.arch.viewmodel.SingleStateViewModel
import com.guerra.enrico.domain.interactors.todos.CreateSuggestion
import com.guerra.enrico.domain.interactors.todos.GetSuggestions
import com.guerra.enrico.domain.interactors.todos.RankUpSuggestion
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.models.todos.Suggestion
import com.guerra.enrico.navigation.models.todos.SearchData
import com.guerra.enrico.todos.search.models.TodoSearchEvent
import com.guerra.enrico.todos.search.models.TodoSearchState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

private const val TYPING_DEBOUNCE = 300L

internal class TodoSearchViewModel @ViewModelInject constructor(
  @CPUDispatcher dispatcher: CoroutineDispatcher,
  private val reducer: TodoSearchReducer,
  private val getSuggestions: GetSuggestions,
  private val createSuggestion: CreateSuggestion,
  private val rankUpSuggestion: RankUpSuggestion
) : SingleStateViewModel<TodoSearchState, TodoSearchEvent>(
  dispatcher = dispatcher,
  initialState = TodoSearchState()
) {

  private val typingFlow = MutableStateFlow("")

  private var job by AutoDisposableJob()

  init {
    typingFlow
      .debounce(TYPING_DEBOUNCE)
      .onEach { text ->
        load(text)
      }
      .launchIn(viewModelScope)
  }

  fun loadWhileTyping(text: String) {
    typingFlow.value = text
  }

  private fun load(text: String) {
    job = viewModelScope.launch {
      val result = getSuggestions(GetSuggestions.Params(text))
      if (result is Result.Success) {
        state = reducer.applySuggestions(state, result.data)
      }
    }
  }

  fun onSearch(text: String) {
    viewModelScope.launch {
      createSuggestion(CreateSuggestion.Params.WithText(text))
      eventsChannel.event = TodoSearchEvent.SearchResult(SearchData(text = text))
    }
  }

  fun onCategoryClick(category: Category) {
    viewModelScope.launch {
      createSuggestion(CreateSuggestion.Params.WithCategory(category))
      eventsChannel.event = TodoSearchEvent.SearchResult(SearchData(category = category))
    }
  }

  fun onSuggestionClick(suggestion: Suggestion) {
    viewModelScope.launch {
      rankUpSuggestion(RankUpSuggestion.Params(suggestion))
      eventsChannel.event = TodoSearchEvent.SearchResult(SearchData(suggestion = suggestion))
    }
  }

  override fun onCleared() {
    super.onCleared()
    job.cancel()
  }
}