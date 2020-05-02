package com.guerra.enrico.domain.observers.todos

import com.guerra.enrico.base.dispatcher.CoroutineDispatcherProvider
import com.guerra.enrico.domain.SubjectInteractor
import com.guerra.enrico.models.todos.Suggestion
import com.guerra.enrico.sera.data.repo.todos.suggestion.SuggestionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by enrico
 * on 02/05/2020.
 */
class ObserveSuggestions @Inject constructor(
  private val suggestionRepository: SuggestionRepository,
  dispatcherProvider: CoroutineDispatcherProvider
) : SubjectInteractor<ObserveSuggestions.Params, List<Suggestion>>() {
  override val dispatcher: CoroutineDispatcher = dispatcherProvider.io()

  override fun createObservable(params: Params): Flow<List<Suggestion>> {
    return suggestionRepository.getSuggestions(params.text)
  }

  class Params(val text: String = "")
}