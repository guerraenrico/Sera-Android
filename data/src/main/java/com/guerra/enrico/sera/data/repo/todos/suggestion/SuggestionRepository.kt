package com.guerra.enrico.sera.data.repo.todos.suggestion

import com.guerra.enrico.models.todos.Suggestion
import kotlinx.coroutines.flow.Flow

/**
 * Created by enrico
 * on 31/03/2020.
 */
interface SuggestionRepository {
  fun getSuggestions(text:String): Flow<List<Suggestion>>
  suspend fun saveSuggestion()
}