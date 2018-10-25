package com.guerra.enrico.sera.ui.todos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.guerra.enrico.sera.R
import kotlinx.android.synthetic.main.item_category_filter.view.*

/**
 * Created by enrico
 * on 21/08/2018.
 */
class CategoryFilterAdapter(
        private val onCategoryClick: (Chip, CategoryFilter) -> Unit
): RecyclerView.Adapter<CategoryViewHolder>() {
    var categoriesFilter = emptyList<CategoryFilter>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_category_filter, parent, false)
        return CategoryViewHolder(itemView)
    }

    override fun getItemCount(): Int = categoriesFilter.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categoriesFilter[position], onCategoryClick)
    }
}

class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bind(categoryFilter: CategoryFilter, onClick: (Chip, CategoryFilter) -> Unit) = with(itemView) {
        categoryChip.text = categoryFilter.category.name
        categoryChip.isChecked = categoryFilter.isChecked.get()
        categoryChip.setOnClickListener { onClick.invoke(categoryChip, categoryFilter) }
    }
}