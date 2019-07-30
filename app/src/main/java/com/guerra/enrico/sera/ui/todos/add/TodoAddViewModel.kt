package com.guerra.enrico.sera.ui.todos.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.data.mediator.category.CreateCategory
import com.guerra.enrico.sera.data.mediator.category.LoadCategoriesFilter
import com.guerra.enrico.sera.data.mediator.task.CreateTask
import com.guerra.enrico.sera.data.result.Result
import com.guerra.enrico.sera.data.result.succeeded
import com.guerra.enrico.sera.ui.base.BaseViewModel
import com.guerra.enrico.sera.ui.todos.add.steps.StepEnum
import com.guerra.enrico.sera.ui.todos.CategoryFilter
import com.guerra.enrico.sera.util.map
import java.util.*
import javax.inject.Inject

/**
 * Created by enrico
 * on 21/10/2018.
 */
class TodoAddViewModel @Inject constructor(
        private val loadCategories: LoadCategoriesFilter,
        private val createCategory: CreateCategory,
        private val createTask: CreateTask
) : BaseViewModel() {
  private val categoriesFilter: LiveData<Result<List<CategoryFilter>>>
  private val loadCategoriesFilterResult: MediatorLiveData<Result<List<CategoryFilter>>> =
          loadCategories.observe()

  private val createdCategory: LiveData<Result<Category>>
  private val createCategoryResult: MediatorLiveData<Result<Category>> = createCategory.observe()

  private val createdTask: LiveData<Result<Task>>
  private val createTaskResult: MediatorLiveData<Result<Task>> = createTask.observe()

  private val selectedCategory = MutableLiveData<Category>()
  private var task = Task()

  private val todoAddCurrentStep = MutableLiveData<StepEnum>()

  init {
    categoriesFilter = loadCategoriesFilterResult.map { it }
    createdCategory = createCategoryResult.map {
      if (it.succeeded) {
        selectedCategory.value = (it as Result.Success).data
      }
      it
    }
    createdTask = createTaskResult.map { it }
    todoAddCurrentStep.value = StepEnum.SELECT
  }

  fun observeCategories(): LiveData<Result<List<CategoryFilter>>> {
    if (!categoriesFilter.hasActiveObservers()) {
      loadCategories.execute(Unit)
    }
    return categoriesFilter
  }

  fun observeSelectedCategory(): LiveData<Category> = selectedCategory

  fun observeTodoAddCurrentStep(): LiveData<StepEnum> = todoAddCurrentStep

  fun observeCreateCategory(): LiveData<Result<Category>> = createdCategory

  fun observeCreateTask(): LiveData<Result<Task>> = createdTask

  fun toggleCategory(categoryFilter: CategoryFilter, checked: Boolean) {
    categoryFilter.isChecked.set(checked)
    selectedCategory.value = if (checked) categoryFilter.category else null
  }

  fun onAddCategory(name: String) {
    val newCategory = Category(name = name)
    selectedCategory.value = newCategory
    createCategory.execute(newCategory)
  }

  fun onSetTaskInfo(title: String, description: String = ""): Boolean {
    val category = selectedCategory.value ?: return false
    task = task.copy(categories = listOf(category), title = title, description = description)
    return true
  }

  fun onAddTask(todoWithin: Date) {
    task = task.copy(todoWithin = todoWithin)
    createTask.execute(task)
  }

  fun goToNextStep(stepEnum: StepEnum) {
    todoAddCurrentStep.value = stepEnum
  }
}