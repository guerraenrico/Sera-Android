package com.guerra.enrico.todos.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.guerra.enrico.base_android.extensions.inflate
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.todos.R

internal class SimpleCategoryAdapter : RecyclerView.Adapter<SimpleCategoryViewHolder>() {
  var categories = emptyList<Category>()

  override fun getItemCount(): Int = categories.size

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleCategoryViewHolder {
    val view = parent.inflate(R.layout.item_simple_category)
    return SimpleCategoryViewHolder(view)
  }

  override fun onBindViewHolder(holder: SimpleCategoryViewHolder, position: Int) {
    holder.bind(categories[position])
  }
}

internal class SimpleCategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
  private val name: TextView = view.findViewById(R.id.categoryName)
  fun bind(category: Category) {
    name.text = category.name
  }
}