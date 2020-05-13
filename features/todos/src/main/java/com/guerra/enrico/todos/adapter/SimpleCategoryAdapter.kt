package com.guerra.enrico.todos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.todos.databinding.ItemSimpleCategoryBinding

/**
 * Created by enrico
 * on 04/01/2020.
 */
internal class SimpleCategoryAdapter : RecyclerView.Adapter<SimpleCategoryViewHolder>() {
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