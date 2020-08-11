package com.guerra.enrico.todos.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.guerra.enrico.base_android.extensions.inflate
import com.guerra.enrico.models.todos.Suggestion
import com.guerra.enrico.todos.R

/**
 * Created by enrico
 * on 02/05/2020.
 */
internal class SuggestionAdapter(
  private val onSuggestionClick: (Suggestion) -> Unit
) : ListAdapter<Suggestion, SuggestionViewHolder>(SuggestionDiff) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionViewHolder {
    val view = parent.inflate(R.layout.item_todos_suggestion)
    return SuggestionViewHolder(view, onSuggestionClick)
  }

  override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
    holder.bind(getItem(position))
  }
}

internal class SuggestionViewHolder(
  view: View,
  private val onClick: (Suggestion) -> Unit
) : RecyclerView.ViewHolder(view) {

  private val container: View = view.findViewById(R.id.container)
  private val label: TextView = view.findViewById(R.id.text)

  fun bind(suggestion: Suggestion) {
    container.setOnClickListener {
      onClick(suggestion)
    }
    label.text = suggestion.text
  }
}

internal object SuggestionDiff : DiffUtil.ItemCallback<Suggestion>() {
  override fun areItemsTheSame(oldItem: Suggestion, newItem: Suggestion): Boolean {
    return oldItem.id == newItem.id
  }

  override fun areContentsTheSame(oldItem: Suggestion, newItem: Suggestion): Boolean {
    return oldItem == newItem
  }
}