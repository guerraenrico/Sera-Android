package com.guerra.enrico.components.recyclerview.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalSpacingItemDecoration (
  private val spacing: Int
) : RecyclerView.ItemDecoration() {

  override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
    val position = parent.getChildAdapterPosition(view)
    outRect.top = spacing
    outRect.right = spacing
    if (position < 1) {
      outRect.left = spacing
    }
    outRect.bottom = spacing
  }
}