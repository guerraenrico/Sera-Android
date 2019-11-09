package com.guerra.enrico.sera.ui.todos.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.guerra.enrico.sera.ui.base.BaseViewModel
import com.guerra.enrico.sera.ui.todos.add.steps.StepEnum
import com.guerra.enrico.sera.ui.todos.CategoryFilter
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject
import com.guerra.enrico.data.Result
import com.guerra.enrico.data.models.Category
import com.guerra.enrico.data.models.Task
import com.guerra.enrico.sera.mediator.category.CreateCategory
import com.guerra.enrico.sera.mediator.category.LoadCategories

/**
 * Created by enrico
 * on 21/10/2018.
 */
class TodoAddViewModel @Inject constructor(
        private val compositeDisposable: CompositeDisposable,
        private val loadCategories: LoadCategories,
        private val createCategory: CreateCategory,
        private val createTask: com.guerra.enrico.sera.mediator.task.CreateTask
) : BaseViewModel(compositeDisposable) {
  private val _categoriesResult: MediatorLiveData<Result<List<Category>>> = loadCategories.observe()

  private val _categoriesFilterResult = MediatorLiveData<Result<List<CategoryFilter>>>()
  val categoriesFilterResult: LiveData<Result<List<CategoryFilter>>>
    get() = _categoriesFilterResult

  private val _createCategoryResult: MediatorLiveData<Result<Category>> = createCategory.observe()
  val createdCategoryResult: LiveData<Result<Category>>
    get() = _createCategoryResult

  private val _createTaskResult: MediatorLiveData<Result<Task>> = createTask.observe()
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

    _selectedCategory.addSource(_createCategoryResult) { result ->
      if (result is Result.Success) {
        _selectedCategory.postValue(result.data)
      }
    }

    _currentStep.value = StepEnum.SELECT

    compositeDisposable.add(loadCategories.execute(Unit))
  }

  fun toggleCategory(categoryFilter: CategoryFilter, checked: Boolean) {
    toggleSelectedCategory(categoryFilter, checked)
  }

  fun onAddCategory(name: String) {
    val newCategory = Category(name = name)
    compositeDisposable.add(createCategory.execute(newCategory))
  }

  fun onSetTaskInfo(title: String, description: String = ""): Boolean {
    val category = selectedCategory.value ?: return false
    task = task.copy(categories = listOf(category), title = title, description = description)
    return true
  }

  fun onAddTask(todoWithin: Date) {
    task = task.copy(todoWithin = todoWithin)
    compositeDisposable.add(createTask.execute(task))
  }

  fun goToNextStep(stepEnum: StepEnum) {
    _currentStep.postValue(stepEnum)
  }

  private fun toggleSelectedCategory(categoryFilter: CategoryFilter, checked: Boolean) {
    _selectedCategory.postValue(if (checked) categoryFilter.category else null)
  }

}