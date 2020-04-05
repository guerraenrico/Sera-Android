package com.guerra.enrico.sera.viewModel.todos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.guerra.enrico.domain.interactors.ApplyTaskUpdateRemote
import com.guerra.enrico.domain.interactors.SyncTasksAndCategories
import com.guerra.enrico.domain.interactors.UpdateTaskCompleteState
import com.guerra.enrico.domain.observers.ObserveCategories
import com.guerra.enrico.domain.observers.ObserveTasks
import com.guerra.enrico.sera.ui.todos.TodosViewModel
import com.guerra.enrico.base.utils.TestCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by enrico
 * on 03/09/2019.
 */
class TodosViewModelTests {
//  @get:Rule
//  val instantTaskExecutorRule = InstantTaskExecutorRule()
//
//  @get:Rule
//  val testCoroutineRule = TestCoroutineRule()
//
//  private var observeCategories: ObserveCategories = mockk()
//  private var observeTasks: ObserveTasks = mockk()
//  private var updateTaskCompleteState: UpdateTaskCompleteState = mockk()
//  private var syncTasksAndCategories: SyncTasksAndCategories = mockk()
//  private var applyTaskUpdateRemote: ApplyTaskUpdateRemote = mockk()
//
//  private lateinit var sut: TodosViewModel
//
//  @Before
//  fun setup() {
//    coEvery { observeTasks(ObserveTasks.Params()) } returns Unit
//    coEvery { observeCategories(Unit) } returns Unit
//  }
//
//  @Test
//  fun `test first load`() = testCoroutineRule.runBlockingTest {
//    // given
//    coEvery { observeTasks.observe() } returns flow { emit(com.guerra.enrico.sera.data.tasks) }
//    coEvery { observeCategories.observe() } returns flow { emit(com.guerra.enrico.sera.data.categories) }
//
//    sut = TodosViewModel(
//      observeCategories,
//      observeTasks,
//      updateTaskCompleteState,
//      syncTasksAndCategories
//    )
//
//    val liveDataTasksViews = sut.tasks.testObserver()
//    val liveDataCategories = sut.categories.testObserver()
//    val liveDataSnackbar = sut.snackbarMessage.testEventObserver()
//
//    // when initial load
//
//    // than
//    Assert.assertEquals(listOf(com.guerra.enrico.sera.data.tasksViewResultSuccess), liveDataTasksViews.observedValues)
//    Assert.assertEquals(listOf(com.guerra.enrico.sera.data.categories), liveDataCategories.observedValues)
//    Assert.assertEquals(emptyList<String>(), liveDataSnackbar.observedValues)
//  }
//
//  @Test
//  fun `test task reload`() = testCoroutineRule.runBlockingTest {
//    // given
//    coEvery { observeTasks.observe() } returns flow { emit(com.guerra.enrico.sera.data.tasks) }
//    coEvery { observeCategories.observe() } returns flow { emit(com.guerra.enrico.sera.data.categories) }
//    coEvery { syncTasksAndCategories(Unit) } returns Unit
//
//    sut = TodosViewModel(
//      observeCategories,
//      observeTasks,
//      updateTaskCompleteState,
//      syncTasksAndCategories
//    )
//
//    val liveDataSwipeRefresh = sut.swipeRefresh.testObserver()
//
//    // when
//    sut.onRefreshData()
//
//    // than
//    Assert.assertEquals(listOf(false, true, false), liveDataSwipeRefresh.observedValues)
//  }

}