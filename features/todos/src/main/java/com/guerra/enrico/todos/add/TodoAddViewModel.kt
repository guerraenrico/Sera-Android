package com.guerra.enrico.todos.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.guerra.enrico.base.dispatcher.CoroutineDispatcherProvider
import com.guerra.enrico.base.Result
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.models.todos.Task
import com.guerra.enrico.domain.interactors.todos.InsertCategory
import com.guerra.enrico.domain.interactors.todos.InsertTask
import com.guerra.enrico.domain.observers.todos.ObserveCategories
import com.guerra.enrico.todos.add.steps.StepEnum
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * Created by enrico
 * on 21/10/2018.
 */
class TodoAddViewModel @Inject constructor(
  private val dispatchers: CoroutineDispatcherProvider,
  observeCategories: ObserveCategories,
  private val insertCategory: InsertCategory,
  private val insertTask: InsertTask
) : ViewModel() {
  private val _categoriesResult: LiveData<Result<List<Category>>> = observeCategories.observe()
    .onStart { Result.Loading }
    .map { Result.Success(it) }
    .asLiveData(dispatchers.io())

  private val _categoriesFilterResult = MediatorLiveData<Result<List<com.guerra.enrico.todos.presentation.CategoryPresentation>>>()
  val categoriesPresentationResult: LiveData<Result<List<com.guerra.enrico.todos.presentation.CategoryPresentation>>>
    get() = _categoriesFilterResult

  private val _createCategoryResult: MediatorLiveData<Result<Category>> = MediatorLiveData()
  val createdCategoryResult: LiveData<Result<Category>>
    get() = _createCategoryResult

  private val _createTaskResult: MediatorLiveData<Result<Task>> = MediatorLiveData()
  val createdTaskResult: LiveData<Result<Task>>
    get() = _createTaskResult

  private val _selectedCategory = MediatorLiveData<Category?>()
  val selectedCategory: Category?
    get() = _selectedCategory.value

  private var task = Task()

  private val _currentStep = MediatorLiveData<StepEnum>()
  val currentStep: LiveData<StepEnum>
    get() = _currentStep

  init {
    _categoriesFilterResult.addSource(_categoriesResult) { result ->
      if (result is Result.Loading) {
        _categoriesFilterResult.postValue(result)
      }
      if (result is Result.Success) {
        _categoriesFilterResult.postValue(Result.Success(result.data.map {
          com.guerra.enrico.todos.presentation.CategoryPresentation(
            it
          )
        }))
      }
      if (result is Result.Error) {
        _categoriesFilterResult.postValue(result)
      }
    }

    _categoriesFilterResult.addSource(_selectedCategory) { category ->
      val categoriesResult = _categoriesFilterResult.value
      if (categoriesResult is Result.Success) {
        _categoriesFilterResult.postValue(Result.Success(categoriesResult.data.map { categoryFilter ->
          return@map com.guerra.enrico.todos.presentation.CategoryPresentation(
            categoryFilter.category,
            categoryFilter.category.id == category?.id
          )
        }))
      }
    }

    _currentStep.value = StepEnum.SELECT
    observeCategories(Unit)
  }

  fun toggleCategory(categoryPresentation: com.guerra.enrico.todos.presentation.CategoryPresentation, checked: Boolean) {
    toggleSelectedCategory(categoryPresentation, checked)
  }

  fun onAddCategory(name: String) {
    val newCategory =
      Category(name = name)
    viewModelScope.launch(dispatchers.io()) {
      _createCategoryResult.postValue(Result.Loading)
      val result = insertCategory(newCategory)
      _createCategoryResult.postValue(result)
      if (result is Result.Success) {
        _selectedCategory.postValue(result.data)
      }
    }
  }

  fun onSetTaskInfo(title: String, description: String = ""): Boolean {
    val category = selectedCategory ?: return false
    task = task.copy(categories = listOf(category), title = title, description = description)
    return true
  }

  fun onAddTask(todoWithin: Date) {
    task = task.copy(todoWithin = todoWithin)
    viewModelScope.launch(dispatchers.io()) {
      _createTaskResult.postValue(Result.Loading)
      val result = insertTask(task)
      _createTaskResult.postValue(result)
    }
  }

  fun goToNextStep(stepEnum: StepEnum) {
    _currentStep.value = stepEnum
  }

  private fun toggleSelectedCategory(categoryPresentation: com.guerra.enrico.todos.presentation.CategoryPresentation, checked: Boolean) {
    _selectedCategory.value = (if (checked) categoryPresentation.category else null)
  }
}