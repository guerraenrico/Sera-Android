package com.guerra.enrico.sera.ui.todos.filter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePaddingRelative
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.guerra.enrico.base.util.activityViewModelProvider
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.ui.todos.TodosViewModel
import com.guerra.enrico.sera.ui.todos.adapter.CategoryAdapter
import com.guerra.enrico.sera.ui.todos.entities.CategoryView
import com.guerra.enrico.sera.widget.GridSpacingItemDecoration
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_todos_filters.*
import javax.inject.Inject

/**
 * Created by enrico
 * on 18/08/2018.
 */
class TodosFilterFragment : BottomSheetDialogFragment() {

  companion object {
    const val TAG = "TodosFilterFragment"
  }

  override fun onCreateView(
          inflater: LayoutInflater,
          container: ViewGroup?,
          savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_todos_filters, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    val gridLayoutManager = GridLayoutManager(context, 2)
    val filterAdapter = CategoryAdapter {}
    recycler_view_categories.apply {
      layoutManager = gridLayoutManager
      adapter = filterAdapter
      addItemDecoration(
        GridSpacingItemDecoration(
          2,
          resources.getDimensionPixelOffset(R.dimen.padding_s),
          true
        )
      )
    }

    recycler_view_categories.doOnApplyWindowInsets { v, insets, padding ->
      v.updatePaddingRelative(bottom = padding.bottom + insets.systemWindowInsetBottom)
    }

    filterAdapter.submitList(getList().map { CategoryView(category = it, isChecked = false) })

    buttonCollapse.setOnClickListener {

    }
  }

  fun View.doOnApplyWindowInsets(f: (View, WindowInsetsCompat, ViewPaddingState) -> Unit) {
    // Create a snapshot of the view's padding state
    val paddingState = createStateForView(this)
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
      f(v, insets, paddingState)
      insets
    }
    requestApplyInsetsWhenAttached()
  }


  private fun createStateForView(view: View) = ViewPaddingState(view.paddingLeft,
    view.paddingTop, view.paddingRight, view.paddingBottom, view.paddingStart, view.paddingEnd)

  data class ViewPaddingState(
    val left: Int,
    val top: Int,
    val right: Int,
    val bottom: Int,
    val start: Int,
    val end: Int
  )

  /**
   * Call [View.requestApplyInsets] in a safe away. If we're attached it calls it straight-away.
   * If not it sets an [View.OnAttachStateChangeListener] and waits to be attached before calling
   * [View.requestApplyInsets].
   */
  fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
      requestApplyInsets()
    } else {
      addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(v: View) {
          v.requestApplyInsets()
        }

        override fun onViewDetachedFromWindow(v: View) = Unit
      })
    }
  }


  fun getList() = listOf(
    Category(id = "1", name = "cat1"),
    Category(id = "2", name = "cat1"),
    Category(id = "3", name = "cat1"),
    Category(id = "4", name = "cat1"),
    Category(id = "5", name = "cat1"),
    Category(id = "6", name = "cat1"),
    Category(id = "7", name = "cat1"),
    Category(id = "8", name = "cat8"),
    Category(id = "9", name = "cat1"),
    Category(id = "10", name = "cat1"),
    Category(id = "11", name = "cat1"),
    Category(id = "12", name = "cat1"),
    Category(id = "13", name = "cat13"),
    Category(id = "14", name = "cat1"),
    Category(id = "15", name = "cat1"),
    Category(id = "16", name = "cat1"),
    Category(id = "17", name = "cat1"),
    Category(id = "18", name = "cat1"),
    Category(id = "19", name = "cat19"),
    Category(id = "20", name = "cat1"),
    Category(id = "21", name = "cat21")
  )
}