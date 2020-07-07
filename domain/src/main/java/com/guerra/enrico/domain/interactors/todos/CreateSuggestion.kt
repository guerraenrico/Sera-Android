package com.guerra.enrico.domain.interactors.todos

import com.guerra.enrico.base.dispatcher.IODispatcher
import com.guerra.enrico.domain.Interactor
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.sera.data.repo.todos.suggestion.SuggestionRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * Created by enrico
 * on 02/05/2020.
 */
class CreateSuggestion @Inject constructor(
  private val suggestionRepository: SuggestionRepository,
  @IODispatcher override val dispatcher: CoroutineDispatcher
) : Interactor<CreateSuggestion.Params, Unit>() {

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