package com.guerra.enrico.todos.adapter

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.guerra.enrico.models.todos.Category

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
      (recyclerView.adapter as? _root_ide_package_.com.guerra.enrico.todos.adapter.SimpleCategoryAdapter
        ?: _root_ide_package_.com.guerra.enrico.todos.adapter.SimpleCategoryAdapter()).apply {
        categories = taskCategories
      }
  }
}