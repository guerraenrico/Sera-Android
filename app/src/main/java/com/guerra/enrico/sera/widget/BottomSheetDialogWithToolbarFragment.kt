package com.guerra.enrico.sera.widget

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.guerra.enrico.base.extensions.awaitOnNextLayout
import com.guerra.enrico.base.extensions.displayMetric
import com.guerra.enrico.sera.R
import kotlinx.android.synthetic.main.fragment_bottom_sheet_dialog_with_toolbar.*
import kotlinx.coroutines.launch
import kotlin.math.min

/**
 * Created by enrico
 * on 02/02/2020.
 */
abstract class BottomSheetDialogWithToolbarFragment : BottomSheetDialogFragment() {

  override fun getTheme(): Int {
    return R.style.BottomSheetDialogTheme
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    return BottomSheetDialogCustom(requireContext(), theme).apply {
      initialState = BottomSheetBehavior.STATE_COLLAPSED
      skipCollapsed = false
      fitContent = true
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_bottom_sheet_dialog_with_toolbar, container, false)

  /**
   * Override returning the layout to be inflated
   */
  abstract fun getContentView(): Int

  private fun wrapContent(layoutResId: Int) {
    val viewToAdd = layoutInflater.inflate(layoutResId, bottom_sheet_container, false)
    bottom_sheet_container.addView(viewToAdd)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    wrapContent(getContentView())
    val bottomSheetDialog = dialog as BottomSheetDialogCustom
    bottomSheetDialog.peakHeight = requireContext().displayMetric().heightPixels / 2
    setupToolbarAnimation(bottomSheetDialog)
  }

  private fun setupToolbarAnimation(bottomSheetDialog: BottomSheetDialogCustom) {
    viewLifecycleOwner.lifecycleScope.launch {
      val toolbarHeight = resources.getDimensionPixelSize(R.dimen.toolbar_height)

      bottom_sheet_container.awaitOnNextLayout()

      if (!shouldShowToolbar(bottom_sheet_container.height, toolbarHeight)) {
        return@launch
      }
      bottomSheetDialog.fitContent = false
      bottomSheetDialog.onBottomSheetSlide = { _, offset ->
        if (offset > 0) {
          bottom_sheet_dialog_toolbar.layoutParams =
            bottom_sheet_dialog_toolbar.layoutParams.apply {
              height = min(toolbarHeight, (toolbarHeight * offset).toInt())
            }
        }
      }

    }

  }

  private fun shouldShowToolbar(containerHeight: Int, toolbarHeight: Int): Boolean {
    return containerHeight >= requireContext().displayMetric().heightPixels - toolbarHeight
  }

}