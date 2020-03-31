package com.guerra.enrico.sera.repo.todos.suggestion

import com.guerra.enrico.sera.data.local.db.LocalDbManager
import com.guerra.enrico.sera.data.models.todos.Suggestion
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by enrico
 * on 31/03/2020.
 */
class SuggestionRepositoryImpl @Inject constructor(
  private val localDbManager: LocalDbManager
) : SuggestionRepository {
  override fun getSuggestions(text: String): Flow<List<Suggestion>> {
    return localDbManager.observeSuggestion(text)
  }

  override suspend fun saveSuggestion() {
  }

}