package com.guerra.enrico.domain.interactors.todos

import com.guerra.enrico.base.dispatcher.CoroutineDispatcherProvider
import com.guerra.enrico.domain.Interactor
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.sera.data.repo.todos.suggestion.SuggestionRepository
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by enrico
 * on 02/05/2020.
 */
class CreateSuggestion(
  private val suggestionRepository: SuggestionRepository,
  coroutineDispatcherProvider: CoroutineDispatcherProvider
) : Interactor<CreateSuggestion.Params, Unit>() {
  override val dispatcher: CoroutineDispatcher = coroutineDispatcherProvider.io()

  override suspend fun doWork(params: Params) {
    when (params) {
      is Params.WithText -> suggestionRepository.createSuggestionFromText(params.text)
      is Params.WithCategory -> suggestionRepository.createSuggestionFromCategory(params.category)
    }
  }

  sealed class Params {
    data class WithText(val text: String) : Params()
    data class WithCategory(val category: Category) : Params()
  }
}