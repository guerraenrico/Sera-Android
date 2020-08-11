package com.guerra.enrico.todos.search

import com.guerra.enrico.models.todos.Suggestion
import com.guerra.enrico.todos.search.models.TodoSearchState
import javax.inject.Inject

class TodoSearchReducer @Inject constructor() {
  fun applySuggestions(state: TodoSearchState, suggestions: List<Suggestion>): TodoSearchState {
    return state.copy(suggestions = suggestions)
  }
}