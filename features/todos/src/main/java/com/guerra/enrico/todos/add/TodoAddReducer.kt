package com.guerra.enrico.todos.add

import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.todos.add.models.TodoAddState
import com.guerra.enrico.todos.add.models.Step
import java.util.*
import javax.inject.Inject

internal class TodoAddReducer @Inject constructor() {

  fun updateCategories(state: TodoAddState, categories: List<Category>): TodoAddState {
    return state.copy(categories = categories)
  }

  fun toggleSelectedCategory(state: TodoAddState, category: Category): TodoAddState {
    val currentSelectedCategory = state.selectedCategory
    if (currentSelectedCategory?.id == category.id) {
      return state.copy(selectedCategory = null)
    }
    return state.copy(selectedCategory = category)
  }

  fun updateTaskInfo(state: TodoAddState, title: String, description: String): TodoAddState {
    val selectedCategory = state.selectedCategory ?: return state.copy(step = Step.SELECT)
    val updatedTask = state.task.copy(
      title = title,
      description = description,
      categories = listOf(selectedCategory)
    )
    return state.copy(task = updatedTask)
  }

  fun updateTaskSchedule(state: TodoAddState, todoWithin: Date): TodoAddState {
    val updatedTask = state.task.copy(todoWithin = todoWithin)
    return state.copy(task = updatedTask)
  }

  fun nextStep(state: TodoAddState, step: Step): TodoAddState {
    return state.copy(step = step)
  }

}