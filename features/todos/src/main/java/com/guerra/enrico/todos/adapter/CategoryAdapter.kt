package com.guerra.enrico.todos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.guerra.enrico.sera.R
import com.guerra.enrico.todos.presentation.CategoryPresentation
import kotlinx.android.synthetic.main.item_category.view.*

/**
 * Created by enrico
 * on 21/08/2018.
 */
enum class ViewSize {
  NORMAL, SMALL
}

class CategoryAdapter(
  private val viewSize: _root_ide_package_.com.guerra.enrico.todos.adapter.ViewSize = _root_ide_package_.com.guerra.enrico.todos.adapter.ViewSize.NORMAL,
  private val onCategoryClick: (com.guerra.enrico.todos.presentation.CategoryPresentation) -> Unit
) : ListAdapter<com.guerra.enrico.todos.presentation.CategoryPresentation, _root_ide_package_.com.guerra.enrico.todos.adapter.CategoryViewHolder>(
  _root_ide_package_.com.guerra.enrico.todos.adapter.CategoryDiff
) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): _root_ide_package_.com.guerra.enrico.todos.adapter.CategoryViewHolder {
    val itemView =
      LayoutInflater.from(parent.context).inflate(getLayout(viewSize), parent, false)
    return _root_ide_package_.com.guerra.enrico.todos.adapter.CategoryViewHolder(itemView)
  }

  override fun onBindViewHolder(holder: _root_ide_package_.com.guerra.enrico.todos.adapter.CategoryViewHolder, position: Int) {
    holder.bind(getItem(position), onCategoryClick)
  }

  private fun getLayout(viewSize: _root_ide_package_.com.guerra.enrico.todos.adapter.ViewSize): Int = when (viewSize) {
    _root_ide_package_.com.guerra.enrico.todos.adapter.ViewSize.NORMAL -> R.layout.item_category
    _root_ide_package_.com.guerra.enrico.todos.adapter.ViewSize.SMALL -> R.layout.item_simple_category
  }
}

class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  fun bind(categoryPresentation: com.guerra.enrico.todos.presentation.CategoryPresentation, onClick: (com.guerra.enrico.todos.presentation.CategoryPresentation) -> Unit) =
    with(itemView) {
      labelCategoryName.text = categoryPresentation.category.name
      labelCategoryName.isSelected = categoryPresentation.isChecked
      labelCategoryName.setOnClickListener { onClick(categoryPresentation) }
    }
}

object CategoryDiff : DiffUtil.ItemCallback<com.guerra.enrico.todos.presentation.CategoryPresentation>() {
  override fun areItemsTheSame(
    oldItem: com.guerra.enrico.todos.presentation.CategoryPresentation,
    newItem: com.guerra.enrico.todos.presentation.CategoryPresentation
  ): Boolean =
    oldItem.category.id == newItem.category.id

  override fun areContentsTheSame(
    oldItem: com.guerra.enrico.todos.presentation.CategoryPresentation,
    newItem: com.guerra.enrico.todos.presentation.CategoryPresentation
  ): Boolean =
    oldItem == newItem
}