package com.guerra.enrico.sera.viewModel.todos

import com.guerra.enrico.sera.*
import com.guerra.enrico.sera.data.mediator.category.LoadCategories
import com.guerra.enrico.sera.data.mediator.task.CompleteTaskEvent
import com.guerra.enrico.sera.data.mediator.task.LoadTasks
import com.guerra.enrico.sera.data.repo.auth.AuthRepository
import com.guerra.enrico.sera.data.repo.category.CategoryRepository
import com.guerra.enrico.sera.data.repo.task.TaskRepository
import com.guerra.enrico.sera.scheduler.SchedulerProvider
import com.guerra.enrico.sera.ui.todos.TodosViewModel
import com.guerra.enrico.sera.utils.testEventObserver
import com.guerra.enrico.sera.utils.testObserver
import io.reactivex.disposables.CompositeDisposable
import org.junit.Assert
import org.junit.Test

/**
 * Created by enrico
 * on 03/09/2019.
 */
class TodosViewModelTests : BaseViewModelTest() {

  lateinit var viewModel: TodosViewModel

  override fun setup() {
    super.setup()
    insertTasks(db)
    insertCategories(db)
  }

  @Test
  fun testFirstLoad() {
    viewModel = createViewModel(
            SchedulerProviderTests(),
            authRepository,
            categoryRepository,
            taskRepository
    )
    val liveDataTasks = viewModel.tasksResult.testObserver()
    val liveDataCategories = viewModel.categories.testObserver()
    val liveDataSnackbar = viewModel.snackbarMessage.testEventObserver()
    Assert.assertEquals(
            listOf(tasksResultSuccess), liveDataTasks.observedValues
    )
    Assert.assertEquals(
            listOf(categories), liveDataCategories.observedValues
    )
    Assert.assertEquals(emptyList<String>(), liveDataSnackbar.observedValues)
  }

  @Test
  fun testReload() {
    viewModel = createViewModel(
            SchedulerProviderTests(),
            authRepository,
            categoryRepository,
            taskRepository
    )
    val liveDataTasks = viewModel.tasksResult.testObserver()
    val liveDataCategories = viewModel.categories.testObserver()
    val liveDataSnackbar = viewModel.snackbarMessage.testEventObserver()
    viewModel.onReloadTasks()
    Assert.assertEquals(
            listOf(tasksResultSuccess, tasksResultLoading, tasksResultSuccess), liveDataTasks.observedValues
    )
    Assert.assertEquals(
            listOf(categories), liveDataCategories.observedValues
    )
    Assert.assertEquals(emptyList<String>(), liveDataSnackbar.observedValues)
  }

  @Test
  fun testErrorLoad() {
    viewModel = createViewModel(
            SchedulerProviderTests(),
            authRepository,
            categoryRepository,
            taskRepository
    )
    val liveDataTasks = viewModel.tasksResult.testObserver()
    val liveDataCategories = viewModel.categories.testObserver()
    val liveDataSnackbar = viewModel.snackbarMessage.testEventObserver()
    viewModel.onReloadTasks()
    Assert.assertEquals(
            listOf(tasksResultSuccess, tasksResultLoading, tasksResultSuccess), liveDataTasks.observedValues
    )
    Assert.assertEquals(
            listOf(categories), liveDataCategories.observedValues
    )
    Assert.assertEquals(emptyList<String>(), liveDataSnackbar.observedValues)
  }

  private fun createViewModel(schedulerProvider: SchedulerProvider, authRepository: AuthRepository, categoryRepository: CategoryRepository, taskRepository: TaskRepository): TodosViewModel =
          TodosViewModel(
                  CompositeDisposable(),
                  LoadCategories(
                          schedulerProvider,
                          authRepository,
                          categoryRepository
                  ),
                  LoadTasks(
                          schedulerProvider,
                          taskRepository
                  ),
                  CompleteTaskEvent(
                          schedulerProvider,
                          authRepository,
                          taskRepository
                  )
          )
}