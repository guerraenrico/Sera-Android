package com.guerra.enrico.todos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.guerra.enrico.models.todos.Suggestion
import com.guerra.enrico.sera.databinding.ItemTodosSuggestionBinding

/**
 * Created by enrico
 * on 02/05/2020.
 */
class SuggestionAdapter(
  private val onSuggestionClick: (Suggestion) -> Unit
) : ListAdapter<Suggestion, SuggestionViewHolder>(SuggestionDiff) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionViewHolder {
    val binding =
      ItemTodosSuggestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return SuggestionViewHolder(binding, onSuggestionClick)
  }

  override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
    holder.bind(getItem(position))
  }
}

class SuggestionViewHolder(
  private val binding: ItemTodosSuggestionBinding,
  private val onClick: (Suggestion) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
  fun bind(suggestion: Suggestion) {
    binding.container.setOnClickListener {
      onClick(suggestion)
    }
    binding.text.text = suggestion.text
  }
}

object SuggestionDiff : DiffUtil.ItemCallback<Suggestion>() {
  override fun areItemsTheSame(oldItem: Suggestion, newItem: Suggestion): Boolean {
    return oldItem.id == newItem.id
  }

  override fun areContentsTheSame(oldItem: Suggestion, newItem: Suggestion): Boolean {
    return oldItem == newItem
  }

}