package com.guerra.enrico.sera.ui.todos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.ui.todos.entities.CategoryView
import kotlinx.android.synthetic.main.item_category.view.*

/**
 * Created by enrico
 * on 21/08/2018.
 */
enum class ViewSize {
  NORMAL, SMALL
}

class CategoryAdapter(
  private val viewSize: ViewSize = ViewSize.NORMAL,
  private val onCategoryClick: (CategoryView) -> Unit
) : ListAdapter<CategoryView, CategoryViewHolder>(
  CategoryDiff
) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
    val itemView =
      LayoutInflater.from(parent.context).inflate(getLayout(viewSize), parent, false)
    return CategoryViewHolder(itemView)
  }

  override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
    holder.bind(getItem(position), onCategoryClick)
  }

  private fun getLayout(viewSize: ViewSize): Int = when (viewSize) {
    ViewSize.NORMAL -> R.layout.item_category
    ViewSize.SMALL -> R.layout.item_simple_category
  }
}

class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  fun bind(categoryView: CategoryView, onClick: (CategoryView) -> Unit) = with(itemView) {
    labelCategoryName.text = categoryView.category.name
    labelCategoryName.isSelected = categoryView.isChecked
    labelCategoryName.setOnClickListener { onClick(categoryView) }
  }
}

object CategoryDiff : DiffUtil.ItemCallback<CategoryView>() {
  override fun areItemsTheSame(oldItem: CategoryView, newItem: CategoryView): Boolean =
    oldItem == newItem

  override fun areContentsTheSame(oldItem: CategoryView, newItem: CategoryView): Boolean =
    oldItem == newItem
}