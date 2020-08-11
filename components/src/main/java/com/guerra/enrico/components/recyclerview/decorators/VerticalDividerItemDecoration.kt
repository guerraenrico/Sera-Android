package com.guerra.enrico.components.recyclerview.decorators

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import com.guerra.enrico.components.R

class VerticalDividerItemDecoration(context: Context) : DividerItemDecoration(context, VERTICAL) {

  init {
    val lineDrawable = context.getDrawable(R.drawable.line_item_divider)!!
    setDrawable(lineDrawable)
  }
}