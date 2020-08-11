package com.guerra.enrico.todos.search.models

import com.guerra.enrico.navigation.models.todos.SearchData

sealed class TodoSearchEvent {
  data class SearchResult(val data: SearchData) : TodoSearchEvent()
}