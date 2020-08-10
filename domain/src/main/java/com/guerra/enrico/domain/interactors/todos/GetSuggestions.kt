package com.guerra.enrico.domain.interactors.todos

import com.guerra.enrico.base.Result
import com.guerra.enrico.base.dispatcher.IODispatcher
import com.guerra.enrico.domain.Interactor
import com.guerra.enrico.models.todos.Suggestion
import com.guerra.enrico.sera.data.repo.todos.suggestion.SuggestionRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetSuggestions @Inject constructor(
  private val suggestionRepository: SuggestionRepository,
  @IODispatcher override val dispatcher: CoroutineDispatcher
) : Interactor<GetSuggestions.Params, Result<List<Suggestion>>>() {

  override suspend fun doWork(params: Params): Result<List<Suggestion>> {
    if (params.text.isBlank()) {
      return suggestionRepository.getSuggestions()
    }
    return suggestionRepository.searchSuggestions(params.text)
  }

  class Params(val text: String = "")
}