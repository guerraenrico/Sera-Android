package com.guerra.enrico.sera.data.repo.todos.suggestion

import com.guerra.enrico.base.Result
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.models.todos.Suggestion

interface SuggestionRepository {
  suspend fun searchSuggestions(text: String): Result<List<Suggestion>>

  suspend fun getSuggestions(): Result<List<Suggestion>>

  suspend fun createSuggestionFromText(text: String)

  suspend fun createSuggestionFromCategory(category: Category)

  suspend fun rankUpSuggestion(suggestion: Suggestion)
}