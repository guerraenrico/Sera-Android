package com.guerra.enrico.todos.models

sealed class TodosEvent {
  data class ShowSnackbar(val snackbarEvent: SnackbarEvent) : TodosEvent()
}

sealed class SnackbarEvent {
  data class UndoCompleteTask(val onAction: () -> Unit, val onDismiss: () -> Unit) : SnackbarEvent()
  data class Message(val exception: Exception) : SnackbarEvent()
}