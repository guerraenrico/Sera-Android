package com.guerra.enrico.sera.viewModel.todos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.guerra.enrico.sera.data.local.db.SeraDatabase
import com.guerra.enrico.sera.DaggerTestComponent
import com.guerra.enrico.sera.TestDataManagerModule
import com.guerra.enrico.sera.TestViewModelModule
import com.guerra.enrico.sera.data.*
import com.guerra.enrico.sera.ui.todos.TodosViewModel
import com.guerra.enrico.sera.utils.TestCoroutineRule
import com.guerra.enrico.sera.utils.testEventObserver
import com.guerra.enrico.sera.utils.testObserver
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

  private val testCoroutineRule = TestCoroutineRule()

  @Inject
  lateinit var database: SeraDatabase
  @Inject
  lateinit var viewModel: TodosViewModel

  @Before
  fun setup(){
    DaggerTestComponent.builder()
            .testDataManagerModule(TestDataManagerModule())
            .testViewModelModule(TestViewModelModule())
            .build()
            .inject(this)

    testCoroutineRule.runBlockingTest {
      insertTasks(database)
      insertCategories(database)
    }
  }

  @Test
  fun testFirstLoad() {
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
    val liveDataTasks = viewModel.tasksResult.testObserver()
    val liveDataCategories = viewModel.categories.testObserver()
    val liveDataSnackbar = viewModel.snackbarMessage.testEventObserver()
    viewModel.onReloadTasks()
    Assert.assertEquals(
            listOf(tasksResultSuccess), liveDataTasks.observedValues
    )
    Assert.assertEquals(
            listOf(categories), liveDataCategories.observedValues
    )
    Assert.assertEquals(emptyList<String>(), liveDataSnackbar.observedValues)
  }

  @Test
  fun testErrorLoad() {
    val liveDataTasks = viewModel.tasksResult.testObserver()
    val liveDataCategories = viewModel.categories.testObserver()
    val liveDataSnackbar = viewModel.snackbarMessage.testEventObserver()
    viewModel.onReloadTasks()
    Assert.assertEquals(
            listOf(tasksResultSuccess), liveDataTasks.observedValues
    )
    Assert.assertEquals(
            listOf(categories), liveDataCategories.observedValues
    )
    Assert.assertEquals(emptyList<String>(), liveDataSnackbar.observedValues)
  }
}