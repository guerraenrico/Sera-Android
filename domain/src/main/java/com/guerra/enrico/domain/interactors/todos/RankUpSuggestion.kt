package com.guerra.enrico.domain.interactors.todos

import com.guerra.enrico.base.dispatcher.IODispatcher
import com.guerra.enrico.domain.Interactor
import com.guerra.enrico.models.todos.Suggestion
import com.guerra.enrico.sera.data.repo.todos.suggestion.SuggestionRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class RankUpSuggestion @Inject constructor(
  private val suggestionRepository: SuggestionRepository,
  @IODispatcher override val dispatcher: CoroutineDispatcher
) : Interactor<RankUpSuggestion.Params, Unit>() {

  override suspend fun doWork(params: Params) {
    suggestionRepository.rankUpSuggestion(params.suggestion)
  }

  data class Params(
    val suggestion: Suggestion
  )
}