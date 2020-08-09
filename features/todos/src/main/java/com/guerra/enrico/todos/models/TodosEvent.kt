package com.guerra.enrico.todos.models

import java.lang.Exception

sealed class TodosEvent {
  data class ShowSnackbar(val snackbarEvent: SnackbarEvent) : TodosEvent()
  data class SwipeRefresh(val enabled: Boolean) : TodosEvent()
}

sealed class SnackbarEvent {
  data class UndoCompleteTask(val onAction: () -> Unit, val onDismiss: () -> Unit) : SnackbarEvent()
  data class Message(val exception: Exception) : SnackbarEvent()
}