package com.guerra.enrico.todos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.guerra.enrico.todos.R
import com.guerra.enrico.todos.presentation.CategoryPresentation
import kotlinx.android.synthetic.main.item_category.view.*

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

internal class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  fun bind(categoryPresentation: CategoryPresentation, onClick: (CategoryPresentation) -> Unit) =
    with(itemView) {
      labelCategoryName.text = categoryPresentation.category.name
      labelCategoryName.isSelected = categoryPresentation.isChecked
      labelCategoryName.setOnClickListener { onClick(categoryPresentation) }
    }
}

internal object CategoryDiff : DiffUtil.ItemCallback<CategoryPresentation>() {
  override fun areItemsTheSame(
    oldItem: CategoryPresentation,
    newItem: CategoryPresentation
  ): Boolean =
    oldItem.category.id == newItem.category.id

  override fun areContentsTheSame(
    oldItem: CategoryPresentation,
    newItem: CategoryPresentation
  ): Boolean =
    oldItem == newItem
}