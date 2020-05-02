package com.guerra.enrico.sera.ui.todos.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.guerra.enrico.base.Result
import com.guerra.enrico.domain.interactors.todos.CreateSuggestion
import com.guerra.enrico.domain.observers.todos.ObserveSuggestions
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.models.todos.Suggestion
import com.guerra.enrico.sera.ui.base.BaseViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by enrico
 * on 16/03/2020.
 */
class TodoSearchViewModel @Inject constructor(
  private val observeSuggestions: ObserveSuggestions,
  private val createSuggestion: CreateSuggestion
) : BaseViewModel() {

  val suggestionsResult: LiveData<Result<List<Suggestion>>> = observeSuggestions.observe()
    .onStart { Result.Loading }
    .map { Result.Success(it) }
    .asLiveData()

  fun onSearch(text: String) {
    viewModelScope.launch {
      createSuggestion(CreateSuggestion.Params.WithText(text))
      // TODO return query to todos activity
    }
  }

  fun onCategorySelected(category: Category) {
    viewModelScope.launch {
      createSuggestion(CreateSuggestion.Params.WithCategory(category))
      // TODO return query to todos activity
    }
  }
}