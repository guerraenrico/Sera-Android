package com.guerra.enrico.sera.ui.todos.filter

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.ui.todos.adapter.CategoryAdapter
import com.guerra.enrico.sera.ui.todos.entities.CategoryView
import kotlinx.android.synthetic.main.fragment_todos_filters.*

/**
 * Created by enrico
 * on 18/08/2018.
 */
class TodosFilterFragment : BottomSheetDialogFragment() {

  companion object {
    const val TAG = "TodosFilterFragment"
  }

  override fun getTheme(): Int {
    return R.style.BottomSheetDialogTheme
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    return BottomSheetDialog(requireContext(), theme)
  }

  lateinit var behavior: BottomSheetBehavior<*>
  private val handler = Handler()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_todos_filters, container, false)
  }

//  override fun onCancel(dialog: DialogInterface) {
//    behavior.state = BottomSheetBehavior.STATE_HIDDEN
//  }
//
//  override fun onStart() {
//    super.onStart()
//    behavior.state = BottomSheetBehavior.STATE_HIDDEN
//    handler.postDelayed({ behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED }, 200)
//
//  }
//
//  override fun dismiss() {
//    behavior.state = BottomSheetBehavior.STATE_HIDDEN
//    handler.postDelayed({  super.dismiss() }, 500)
//  }


  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    val bottomSheet = dialog?.findViewById(
      com.google.android.material.R.id.design_bottom_sheet
    ) as FrameLayout
    behavior = BottomSheetBehavior.from(bottomSheet)

    behavior.peekHeight = -1
    behavior.skipCollapsed = true

//    behavior.setBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback() {
//      override fun onSlide(p0: View, p1: Float) {
//
//      }
//
//      override fun onStateChanged(p0: View, newState: Int) {
////        if (newState == BottomSheetBehavior.STATE_HIDDEN && dialog?.isShowing == true) {
////          dialog?.dismiss()
////        }
//      }
//
//    })

    sheetTitle.setOnClickListener {
      behavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    val gridLayoutManager =
      LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    val filterAdapter = CategoryAdapter {}
    recycler_view_categories.apply {
      layoutManager = gridLayoutManager
      adapter = filterAdapter
    }

//    recycler_view_categories.doOnApplyWindowInsets { v, insets, padding ->
//      v.updatePaddingRelative(bottom = padding.bottom + insets.systemWindowInsetBottom)
//    }

    filterAdapter.submitList(getList().map { CategoryView(category = it, isChecked = false) })

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

  private fun createStateForView(view: View) = ViewPaddingState(
    view.paddingLeft,
    view.paddingTop, view.paddingRight, view.paddingBottom, view.paddingStart, view.paddingEnd
  )

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