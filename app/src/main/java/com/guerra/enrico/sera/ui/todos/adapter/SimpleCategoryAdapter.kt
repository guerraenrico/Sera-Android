package com.guerra.enrico.sera.ui.todos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.databinding.ItemSimpleCategoryBinding
import kotlinx.android.synthetic.main.item_category.view.*

/**
 * Created by enrico
 * on 04/01/2020.
 */
class SimpleCategoryAdapter : RecyclerView.Adapter<SimpleCategoryViewHolder>() {
  var categories = emptyList<Category>()

  override fun getItemCount(): Int = categories.size

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    SimpleCategoryViewHolder(
      ItemSimpleCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

  override fun onBindViewHolder(holder: SimpleCategoryViewHolder, position: Int) {
    holder.bind(categories[position])
  }
}

class SimpleCategoryViewHolder(private val binding: ItemSimpleCategoryBinding) :
  RecyclerView.ViewHolder(binding.root) {
  fun bind(category: Category) {
    binding.category = category
    binding.executePendingBindings()
  }
}