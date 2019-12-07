package com.guerra.enrico.sera.ui.todos.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.guerra.enrico.base.dispatcher.CoroutineContextProvider
import com.guerra.enrico.data.Result
import com.guerra.enrico.data.models.Category
import com.guerra.enrico.data.models.Task
import com.guerra.enrico.domain.interactors.InsertCategory
import com.guerra.enrico.domain.interactors.InsertTask
import com.guerra.enrico.domain.observers.ObserveCategories
import com.guerra.enrico.sera.ui.base.BaseViewModel
import com.guerra.enrico.sera.ui.todos.CategoryFilter
import com.guerra.enrico.sera.ui.todos.add.steps.StepEnum
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
        private val dispatchers: CoroutineContextProvider,
        observeCategories: ObserveCategories,
        private val insertCategory: InsertCategory,
        private val insertTask: InsertTask
) : BaseViewModel() {
  private val _categoriesResult: LiveData<Result<List<Category>>> = observeCategories.observe()
          .onStart { Result.Loading }
          .map { Result.Success(it) }
          .asLiveData(dispatchers.io())

  private val _categoriesFilterResult = MediatorLiveData<Result<List<CategoryFilter>>>()
  val categoriesFilterResult: LiveData<Result<List<CategoryFilter>>>
    get() = _categoriesFilterResult

  private val _createCategoryResult: MediatorLiveData<Result<Category>> = MediatorLiveData()
  val createdCategoryResult: LiveData<Result<Category>>
    get() = _createCategoryResult

  private val _createTaskResult: MediatorLiveData<Result<Task>> = MediatorLiveData()
  val createdTaskResult: LiveData<Result<Task>>
    get() = _createTaskResult

  private val _selectedCategory = MediatorLiveData<Category>()
  val selectedCategory: LiveData<Category>
    get() = _selectedCategory

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
        _categoriesFilterResult.postValue(Result.Success(result.data.map { CategoryFilter(it) }))
      }
      if (result is Result.Error) {
        _categoriesFilterResult.postValue(result)
      }
    }

    _categoriesFilterResult.addSource(_selectedCategory) { category ->
      val categoriesResult = _categoriesFilterResult.value
      if (categoriesResult is Result.Success) {
        _categoriesFilterResult.postValue(Result.Success(categoriesResult.data.map { categoryFilter ->
          return@map CategoryFilter(categoryFilter.category, categoryFilter.category.id == category.id)
        }))
      }
    }

    _currentStep.value = StepEnum.SELECT
    observeCategories(Unit)
  }

  fun toggleCategory(categoryFilter: CategoryFilter, checked: Boolean) {
    toggleSelectedCategory(categoryFilter, checked)
  }

  fun onAddCategory(name: String) {
    val newCategory = Category(name = name)
    viewModelScope.launch(dispatchers.io()) {
      _createCategoryResult.postValue(Result.Loading)
      val result = insertCategory.execute(newCategory)
      _createCategoryResult.postValue(result)
      if (result is Result.Success) {
        _selectedCategory.postValue(result.data)
      }
    }
  }

  fun onSetTaskInfo(title: String, description: String = ""): Boolean {
    val category = selectedCategory.value ?: return false
    task = task.copy(categories = listOf(category), title = title, description = description)
    return true
  }

  fun onAddTask(todoWithin: Date) {
    task = task.copy(todoWithin = todoWithin)
    viewModelScope.launch(dispatchers.io()) {
      _createTaskResult.postValue(Result.Loading)
      val result = insertTask.execute(task)
      _createTaskResult.postValue(result)
    }
  }

  fun goToNextStep(stepEnum: StepEnum) {
    _currentStep.postValue(stepEnum)
  }

  private fun toggleSelectedCategory(categoryFilter: CategoryFilter, checked: Boolean) {
    _selectedCategory.postValue(if (checked) categoryFilter.category else null)
  }
}