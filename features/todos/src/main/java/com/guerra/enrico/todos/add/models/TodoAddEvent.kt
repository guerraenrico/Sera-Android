package com.guerra.enrico.todos.add.models

sealed class TodoAddEvent {
  data class ShowSnackbar(val exception: Exception) : TodoAddEvent()
}