package com.guerra.enrico.sera.data.repo.todos.suggestion

import com.guerra.enrico.local.db.LocalDbManager
import com.guerra.enrico.models.todos.Suggestion
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by enrico
 * on 31/03/2020.
 */
internal class SuggestionRepositoryImpl @Inject constructor(
  private val localDbManager: LocalDbManager
) : SuggestionRepository {
  override fun getSuggestions(text: String): Flow<List<Suggestion>> {
    return localDbManager.observeSuggestion(text)
  }

  override suspend fun saveSuggestion() {
  }

}