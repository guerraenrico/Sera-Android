package com.guerra.enrico.todos.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.guerra.enrico.base_android.extensions.inflate
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.todos.R

internal enum class ViewSize {
  NORMAL, SMALL
}

internal class CategoryAdapter(
  private val viewSize: ViewSize = ViewSize.NORMAL,
  private val onCategoryClick: (Category) -> Unit
) : ListAdapter<Category, CategoryViewHolder>(CategoryDiff) {

  private var selectedCategory: Category? = null

  fun updateSelectedCategory(category: Category?) {
    if (selectedCategory == category) return

    val selectedPosition = currentList.indexOf(selectedCategory)
    val newSelectedPosition = currentList.indexOf(category)
    selectedCategory = category
    if (selectedPosition != -1) {
      notifyItemChanged(selectedPosition)
    }
    if (newSelectedPosition != -1) {
      notifyItemChanged(newSelectedPosition)
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
    val itemView = parent.inflate(viewType)
    return CategoryViewHolder(itemView)
  }

  override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
    val category = getItem(position)
    holder.bind(category, onCategoryClick, category.id == selectedCategory?.id)
  }

  override fun getItemViewType(position: Int): Int {
    return when (viewSize) {
      ViewSize.NORMAL -> R.layout.item_category
      ViewSize.SMALL -> R.layout.item_simple_category
    }
  }
}

internal class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

  private val name: TextView = itemView.findViewById(R.id.categoryName)

  fun bind(category: Category, onClick: (Category) -> Unit, isSelected: Boolean) {
    name.text = category.name
    name.isSelected = isSelected
    name.setOnClickListener { onClick(category) }
  }
}

internal object CategoryDiff : DiffUtil.ItemCallback<Category>() {
  override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
    return oldItem.id == newItem.id
  }

  override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
    return oldItem == newItem
  }
}