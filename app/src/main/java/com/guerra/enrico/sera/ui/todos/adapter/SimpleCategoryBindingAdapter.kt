package com.guerra.enrico.sera.ui.todos.adapter

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.guerra.enrico.sera.data.models.Category

/**
 * Created by enrico
 * on 04/01/2020.
 */
@BindingAdapter("taskCategories")
fun taskCategories(recyclerView: RecyclerView, taskCategories: List<Category>) {
  if (taskCategories.isEmpty()) {
    recyclerView.visibility = View.GONE
  } else {
    recyclerView.visibility = View.VISIBLE
    recyclerView.adapter =
      (recyclerView.adapter as? SimpleCategoryAdapter ?: SimpleCategoryAdapter()).apply {
        categories = taskCategories
      }
  }
}