package com.guerra.enrico.sera.viewModel.todos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.guerra.enrico.domain.interactors.ApplyTaskUpdateRemote
import com.guerra.enrico.domain.interactors.SyncTasksAndCategories
import com.guerra.enrico.domain.interactors.UpdateTaskCompleteState
import com.guerra.enrico.domain.observers.ObserveCategories
import com.guerra.enrico.domain.observers.ObserveTasks
import com.guerra.enrico.sera.DaggerTestComponent
import com.guerra.enrico.sera.TestDataManagerModule
import com.guerra.enrico.sera.data.*
import com.guerra.enrico.sera.ui.todos.TodosViewModel
import com.guerra.enrico.sera.utils.TestCoroutineRule
import com.guerra.enrico.sera.utils.testEventObserver
import com.guerra.enrico.sera.utils.testObserver
import io.mockk.coEvery
import io.mockk.spyk
import kotlinx.coroutines.flow.flow
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

/**
 * Created by enrico
 * on 03/09/2019.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class TodosViewModelTests {
  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val testCoroutineRule = TestCoroutineRule()

  @Inject
  lateinit var observeCategories: ObserveCategories
  @Inject
  lateinit var observeTasks: ObserveTasks
  @Inject
  lateinit var updateTaskCompleteState: UpdateTaskCompleteState
  @Inject
  lateinit var syncTasksAndCategories: SyncTasksAndCategories
  @Inject
  lateinit var applyTaskUpdateRemote: ApplyTaskUpdateRemote

  private lateinit var viewModel: TodosViewModel

  @Before
  fun setup() {
    DaggerTestComponent.builder()
      .testDataManagerModule(TestDataManagerModule())
      .build()
      .inject(this)

    observeCategories = spyk(observeCategories, recordPrivateCalls = true)
    observeTasks = spyk(observeTasks, recordPrivateCalls = true)
    updateTaskCompleteState = spyk(updateTaskCompleteState, recordPrivateCalls = true)
    syncTasksAndCategories = spyk(syncTasksAndCategories, recordPrivateCalls = true)
    applyTaskUpdateRemote = spyk(applyTaskUpdateRemote, recordPrivateCalls = true)

    viewModel = TodosViewModel(
      observeCategories,
      observeTasks,
      updateTaskCompleteState,
      syncTasksAndCategories,
      applyTaskUpdateRemote
    )
  }

  @Test
  fun `test first load`() {
    testCoroutineRule.runBlockingTest {
      // given
      val params = ObserveTasks.Params()
      coEvery { observeTasks["createObservable"] (params) } returns flow { emit(tasks) }
      coEvery { observeCategories["createObservable"] (Unit) } returns flow { emit(categories) }

      val liveDataTasksViews = viewModel.tasksViewResult.testObserver()
      val liveDataCategories = viewModel.categories.testObserver()
      val liveDataSnackbar = viewModel.snackbarMessage.testEventObserver()

      // when initial load

      // than
      Assert.assertEquals(listOf(tasksViewResultSuccess), liveDataTasksViews.observedValues)
      Assert.assertEquals(listOf(categories), liveDataCategories.observedValues)
      Assert.assertEquals(emptyList<String>(), liveDataSnackbar.observedValues)
    }
  }

  @Test
  fun `test task reload`() {
    testCoroutineRule.runBlockingTest {
      // given
      coEvery { syncTasksAndCategories["doWork"] (Unit) } returns Unit
      val liveDataSwipeRefresh = viewModel.swipeRefresh.testObserver()

      // when
      viewModel.onRefreshData()

      // than
      Assert.assertEquals(listOf(false, true, false), liveDataSwipeRefresh.observedValues)
    }
  }
}