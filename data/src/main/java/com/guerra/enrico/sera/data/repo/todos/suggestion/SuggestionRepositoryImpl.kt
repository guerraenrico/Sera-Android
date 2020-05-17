package com.guerra.enrico.sera.data.repo.todos.suggestion

import com.guerra.enrico.base.Result
import com.guerra.enrico.local.db.LocalDbManager
import com.guerra.enrico.models.EntityData
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.models.todos.Suggestion
import java.util.*
import javax.inject.Inject

/**
 * Created by enrico
 * on 31/03/2020.
 */
class SuggestionRepositoryImpl @Inject constructor(
  private val localDbManager: LocalDbManager
) : SuggestionRepository {

  override suspend fun searchSuggestions(text: String): Result<List<Suggestion>> {
    return Result.Success(localDbManager.searchSuggestions(text))
  }

  override suspend fun getSuggestions(): Result<List<Suggestion>> {
    return Result.Success(localDbManager.getSuggestions())
  }

  override suspend fun createSuggestionFromText(text: String) {
    val suggestion = Suggestion(text = text, rating = 1.0)
    localDbManager.insertSuggestion(suggestion)
  }

  override suspend fun createSuggestionFromCategory(category: Category) {
    val suggestion = Suggestion(
      text = category.name,
      rating = 1.0,
      entityData = EntityData(category.id, category.name)
    )
    localDbManager.insertSuggestion(suggestion)
  }

  override suspend fun rankUpSuggestion(suggestion: Suggestion) {
    val rankedSuggestion = suggestion.copy(rating = suggestion.rating + 1.0, updatedAt = Date())
    localDbManager.updateSuggestion(rankedSuggestion)
  }

}