package com.guerra.enrico.todos.search.models

import com.guerra.enrico.models.todos.Suggestion

data class TodoSearchState(val suggestions: List<Suggestion> = emptyList())