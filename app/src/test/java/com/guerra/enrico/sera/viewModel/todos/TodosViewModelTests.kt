package com.guerra.enrico.sera.viewModel.todos

//import com.guerra.enrico.base.dispatcher.AppDispatchers
//import com.guerra.enrico.base.dispatcher.AppDispatchersImpl
//import com.guerra.enrico.sera.*
//import com.guerra.enrico.base.scheduler.SchedulerProvider
//import com.guerra.enrico.domain.observers.ObserveCategories
//import com.guerra.enrico.sera.ui.todos.TodosViewModel
//import com.guerra.enrico.sera.utils.testEventObserver
//import com.guerra.enrico.sera.utils.testObserver
//import com.nhaarman.mockitokotlin2.mock
//import io.reactivex.disposables.CompositeDisposable
//import org.junit.Assert
//import org.junit.Test
//
///**
// * Created by enrico
// * on 03/09/2019.
// */
//class TodosViewModelTests : BaseViewModelTest() {
//
//  var viewModel: TodosViewModel = mock()
//
//  override fun setup() {
//    super.setup()
//    insertTasks(db)
//    insertCategories(db)
//  }
//
//  @Test
//  fun testFirstLoad() {
//    val liveDataTasks = viewModel.tasksResult.testObserver()
//    val liveDataCategories = viewModel.categories.testObserver()
//    val liveDataSnackbar = viewModel.snackbarMessage.testEventObserver()
//    Assert.assertEquals(
//            listOf(tasksResultSuccess), liveDataTasks.observedValues
//    )
//    Assert.assertEquals(
//            listOf(categories), liveDataCategories.observedValues
//    )
//    Assert.assertEquals(emptyList<String>(), liveDataSnackbar.observedValues)
//  }
//
//  @Test
//  fun testReload() {
//    val liveDataTasks = viewModel.tasksResult.testObserver()
//    val liveDataCategories = viewModel.categories.testObserver()
//    val liveDataSnackbar = viewModel.snackbarMessage.testEventObserver()
//    viewModel.onReloadTasks()
//    Assert.assertEquals(
//            listOf(tasksResultSuccess, tasksResultLoading, tasksResultSuccess), liveDataTasks.observedValues
//    )
//    Assert.assertEquals(
//            listOf(categories), liveDataCategories.observedValues
//    )
//    Assert.assertEquals(emptyList<String>(), liveDataSnackbar.observedValues)
//  }
//
//  @Test
//  fun testErrorLoad() {
//    val liveDataTasks = viewModel.tasksResult.testObserver()
//    val liveDataCategories = viewModel.categories.testObserver()
//    val liveDataSnackbar = viewModel.snackbarMessage.testEventObserver()
//    viewModel.onReloadTasks()
//    Assert.assertEquals(
//            listOf(tasksResultSuccess, tasksResultLoading, tasksResultSuccess), liveDataTasks.observedValues
//    )
//    Assert.assertEquals(
//            listOf(categories), liveDataCategories.observedValues
//    )
//    Assert.assertEquals(emptyList<String>(), liveDataSnackbar.observedValues)
//  }
//}