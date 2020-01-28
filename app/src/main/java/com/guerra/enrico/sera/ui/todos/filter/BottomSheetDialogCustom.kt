package com.guerra.enrico.sera.ui.todos.filter

import android.content.Context
import android.content.DialogInterface
import android.content.res.TypedArray
import android.os.Build.VERSION
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.annotation.IntDef
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.guerra.enrico.sera.R

/**
 * Created by enrico
 * on 26/01/2020.
 *
 * This class is base on BottomSheetDialog class from material component
 * and it handle the following behavior:
 * - perform the swipe down animation when dismissing instead of the window animation for the dialog.
 * - perform the swipe up animation when open instead of the window animation for the dialog.
 */

@Retention(AnnotationRetention.SOURCE)
@IntDef(
  BottomSheetBehavior.STATE_SETTLING,
  BottomSheetBehavior.STATE_EXPANDED,
  BottomSheetBehavior.STATE_COLLAPSED,
  BottomSheetBehavior.STATE_HIDDEN,
  BottomSheetBehavior.STATE_HALF_EXPANDED
)
annotation class BottomSheetBehaviorState

typealias OnBottomSheetSlide = (bottomSheet: View, offset: Float) -> Unit

class BottomSheetDialogCustom : AppCompatDialog {
  private lateinit var behavior: BottomSheetBehavior<FrameLayout>
  private var cancelable = false
  private var canceledOnTouchOutside = false
  private var canceledOnTouchOutsideSet = false
  var onBottomSheetSlide: OnBottomSheetSlide? = null
  var peakHeight: Int = -1
  var skipCollapsed = false
  var fitContent = true

  @BottomSheetBehaviorState
  var initialState = BottomSheetBehavior.STATE_HALF_EXPANDED

  private val bottomSheetCallback: BottomSheetCallback = object : BottomSheetCallback() {
    override fun onSlide(bottomSheet: View, offset: Float) {
      onBottomSheetSlide?.invoke(bottomSheet, offset)
    }

    override fun onStateChanged(bottomSheet: View, newState: Int) {
      if (newState == BottomSheetBehavior.STATE_HIDDEN) {
        cancel()
      }
    }
  }

  constructor(context: Context) :
    this(context, 0)

  constructor(context: Context, @StyleRes theme: Int) : super(
    context, getThemeResId(context, theme)
  ) {
    this.cancelable = true
    this.canceledOnTouchOutside = true
    this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
  }

  constructor(
    context: Context,
    cancelable: Boolean,
    cancelListener: DialogInterface.OnCancelListener?
  ) : super(context, cancelable, cancelListener) {
    canceledOnTouchOutside = true
    supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
    this.cancelable = cancelable
  }

  override fun setContentView(@LayoutRes layoutResId: Int) {
    super.setContentView(wrapInBottomSheet(layoutResId, null, null))
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    window?.apply {
      if (VERSION.SDK_INT >= 21) {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
      }
      setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }
  }

  override fun setContentView(view: View) {
    super.setContentView(this.wrapInBottomSheet(0, view, null))
  }

  override fun setContentView(view: View, params: ViewGroup.LayoutParams?) {
    super.setContentView(this.wrapInBottomSheet(0, view, params))
  }

  override fun setCancelable(cancelable: Boolean) {
    super.setCancelable(cancelable)
    if (this.cancelable != cancelable) {
      this.cancelable = cancelable
      this.behavior.isHideable = cancelable
    }
  }

  override fun onStart() {
    super.onStart()
    behavior.state = BottomSheetBehavior.STATE_HIDDEN
    behavior.skipCollapsed = skipCollapsed
    behavior.peekHeight = peakHeight
    behavior.isFitToContents = fitContent
  }

  override fun cancel() {
    if (behavior.state == BottomSheetBehavior.STATE_HIDDEN) {
      super.cancel()
    } else {
      behavior.state = BottomSheetBehavior.STATE_HIDDEN
    }
  }

  override fun setOnShowListener(listener: DialogInterface.OnShowListener?) {
    super.setOnShowListener {
      // Important: set the initial state when the dialog is showing show the animation in visible
      behavior.state = initialState
      listener?.onShow(it)
    }
  }

  override fun setCanceledOnTouchOutside(cancel: Boolean) {
    super.setCanceledOnTouchOutside(cancel)
    if (cancel && !cancelable) {
      this.cancelable = true
    }
    this.canceledOnTouchOutside = cancel
    this.canceledOnTouchOutsideSet = true
  }

  fun setBehaviorState(@BottomSheetBehaviorState state: Int) {
    if (::behavior.isInitialized) {
      behavior.state = state
    }
  }

  private fun wrapInBottomSheet(
    layoutResId: Int,
    view: View?,
    params: ViewGroup.LayoutParams?
  ): View {
    var viewToAdd: View? = view
    val container = View.inflate(context, R.layout.bottom_sheet_dialog, null) as FrameLayout
    val coordinator = container.findViewById<View>(R.id.coordinator) as CoordinatorLayout
    if (layoutResId != 0 && viewToAdd == null) {
      viewToAdd = layoutInflater.inflate(layoutResId, coordinator, false)
    }
    val bottomSheet = coordinator.findViewById<View>(R.id.bottom_sheet) as FrameLayout
    behavior = BottomSheetBehavior.from(bottomSheet).apply {
      setBottomSheetCallback(bottomSheetCallback)
      isHideable = cancelable
    }

    if (params == null) {
      bottomSheet.addView(viewToAdd)
    } else {
      bottomSheet.addView(viewToAdd, params)
    }
    coordinator.findViewById<View>(R.id.touch_outside)
      .setOnClickListener {
        if (cancelable && isShowing && shouldWindowCloseOnTouchOutside()) {
          cancel()
        }
      }

    ViewCompat.setAccessibilityDelegate(
      bottomSheet,
      object : AccessibilityDelegateCompat() {
        override fun onInitializeAccessibilityNodeInfo(
          host: View,
          info: AccessibilityNodeInfoCompat
        ) {
          super.onInitializeAccessibilityNodeInfo(host, info)
          if (cancelable) {
            info.addAction(AccessibilityNodeInfoCompat.ACTION_DISMISS)
            info.isDismissable = true
          } else {
            info.isDismissable = false
          }
        }

        override fun performAccessibilityAction(host: View, action: Int, args: Bundle): Boolean {
          return if (action == AccessibilityNodeInfoCompat.ACTION_DISMISS && cancelable) {
            cancel()
            true
          } else {
            super.performAccessibilityAction(host, action, args)
          }
        }
      })

    bottomSheet.setOnTouchListener { view, event -> true }

    // Init to set the initial behavior state
    setOnShowListener(null)

    return container
  }

  private fun shouldWindowCloseOnTouchOutside(): Boolean {
    if (!this.canceledOnTouchOutsideSet) {
      val a: TypedArray =
        context.obtainStyledAttributes(intArrayOf(android.R.attr.windowCloseOnTouchOutside))
      canceledOnTouchOutside = a.getBoolean(0, true)
      a.recycle()
      canceledOnTouchOutsideSet = true
    }
    return canceledOnTouchOutside
  }

  fun updateHeight(height: Int) {

  }

  companion object {
    private fun getThemeResId(context: Context, themeId: Int): Int {
      if (themeId == 0) {
        val outValue = TypedValue()
        return if (context.theme.resolveAttribute(R.attr.bottomSheetDialogTheme, outValue, true)) {
          outValue.resourceId
        } else {
          R.style.DefaultBottomSheetDialog
        }
      }
      return themeId
    }
  }
}