package com.guerra.enrico.sera.ui.todos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.guerra.enrico.sera.R
import kotlinx.android.synthetic.main.item_category.view.*

/**
 * Created by enrico
 * on 21/08/2018.
 */
class CategoryFilterAdapter(
        private val onCategoryClick: (CategoryFilter) -> Unit
) : RecyclerView.Adapter<CategoryFilterViewHolder>() {
  private var categoriesFilter = emptyList<CategoryFilter>()

  fun updateList(list: List<CategoryFilter>) {
    val diffRes = DiffUtil.calculateDiff(CategoriesFilterDiffCallback(categoriesFilter, list))
    categoriesFilter = list
    diffRes.dispatchUpdatesTo(this)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryFilterViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
    return CategoryFilterViewHolder(itemView)
  }

  override fun getItemCount(): Int = categoriesFilter.size

  override fun onBindViewHolder(holder: CategoryFilterViewHolder, position: Int) {
    holder.bind(categoriesFilter[position], onCategoryClick)
  }
}

class CategoryFilterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  fun bind(categoryFilter: CategoryFilter, onClick: (CategoryFilter) -> Unit) = with(itemView) {
    labelCategoryName.text = categoryFilter.category.name
    labelCategoryName.isSelected = categoryFilter.isChecked
    contentCategory.isSelected = categoryFilter.isChecked
    contentCategory.setOnClickListener { onClick.invoke(categoryFilter) }
  }
}

class CategoriesFilterDiffCallback(
        private val oldList: List<CategoryFilter>,
        private val newList: List<CategoryFilter>
) : DiffUtil.Callback() {
  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition].category.id == newList[newItemPosition].category.id

  override fun getOldListSize(): Int = oldList.size

  override fun getNewListSize(): Int = newList.size

  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
          oldList[oldItemPosition].isChecked == newList[newItemPosition].isChecked
}