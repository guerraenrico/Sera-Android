package com.guerra.enrico.todos.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.guerra.enrico.base_android.extensions.inflate
import com.guerra.enrico.todos.R
import com.guerra.enrico.todos.presentation.CategoryPresentation

/**
 * Created by enrico
 * on 21/08/2018.
 */
internal enum class ViewSize {
  NORMAL, SMALL
}

internal class CategoryAdapter(
  private val viewSize: ViewSize = ViewSize.NORMAL,
  private val onCategoryClick: (CategoryPresentation) -> Unit
) : ListAdapter<CategoryPresentation, CategoryViewHolder>(CategoryDiff) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
    val itemView = parent.inflate(viewType)
    return CategoryViewHolder(itemView)
  }

  override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
    holder.bind(getItem(position), onCategoryClick)
  }

  override fun getItemViewType(position: Int): Int {
    return when (viewSize) {
      ViewSize.NORMAL -> R.layout.item_category
      ViewSize.SMALL -> R.layout.item_simple_category
    }
  }
}

internal class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

  private val name: TextView = view.findViewById(R.id.label_category_name)

  fun bind(categoryPresentation: CategoryPresentation, onClick: (CategoryPresentation) -> Unit) {
    name.text = categoryPresentation.category.name
    name.isSelected = categoryPresentation.isChecked
    name.setOnClickListener { onClick(categoryPresentation) }
  }
}

internal object CategoryDiff : DiffUtil.ItemCallback<CategoryPresentation>() {
  override fun areItemsTheSame(
    oldItem: CategoryPresentation,
    newItem: CategoryPresentation
  ): Boolean {
    return oldItem.category.id == newItem.category.id
  }

  override fun areContentsTheSame(
    oldItem: CategoryPresentation,
    newItem: CategoryPresentation
  ): Boolean {
    return oldItem == newItem
  }

}