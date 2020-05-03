package com.guerra.enrico.sera.ui.todos.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.guerra.enrico.base.Result
import com.guerra.enrico.base.coroutine.AutoDisposableJob
import com.guerra.enrico.domain.interactors.todos.CreateSuggestion
import com.guerra.enrico.domain.interactors.todos.GetSuggestions
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.models.todos.Suggestion
import com.guerra.enrico.sera.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by enrico
 * on 16/03/2020.
 */
class TodoSearchViewModel @Inject constructor(
  private val getSuggestions: GetSuggestions,
  private val createSuggestion: CreateSuggestion
) : BaseViewModel() {

  private val _suggestionsResult = MutableLiveData<Result<List<Suggestion>>>(Result.Loading)
  val suggestionsResult: LiveData<Result<List<Suggestion>>>
    get() = _suggestionsResult

  var job by AutoDisposableJob()

  fun load() {
    job = viewModelScope.launch {
      _suggestionsResult.value = getSuggestions(GetSuggestions.Params())
    }
  }

  fun loadWhileTyping(text: String) {
    job = viewModelScope.launch {
      _suggestionsResult.value = getSuggestions(GetSuggestions.Params(text))
    }
  }

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