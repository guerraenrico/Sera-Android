package com.guerra.enrico.todos.adapter

import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.guerra.enrico.base.extensions.toDateString
import com.guerra.enrico.todos.R
import java.util.*

/**
 * Created by enrico
 * on 04/01/2020.
 */

@BindingAdapter(
  "date",
  requireAll = true
)
fun setDate(textView: AppCompatTextView, previousDate: Date?, newDate: Date) {
  if (newDate != previousDate) {
    with(textView) {
      text = String.format(
        resources.getString(R.string.label_todo_within),
        newDate.toDateString()
      )
    }
  }
}
