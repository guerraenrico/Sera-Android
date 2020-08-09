package com.guerra.enrico.settings

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.guerra.enrico.base_android.extensions.inflate
import com.guerra.enrico.settings.model.Option
import java.security.InvalidKeyException

internal class SettingAdapter(
  private val eventActions: EventActions
) : ListAdapter<Option, RecyclerView.ViewHolder>(OptionItemCallback) {
  companion object {
    private const val TOGGLE = 1
  }

  override fun getItemViewType(position: Int): Int {
    return when (getItem(position)) {
      is Option.Toggle -> TOGGLE
      else -> -1
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return when (viewType) {
      TOGGLE -> {
        val view = parent.inflate(R.layout.item_setting_option_toggle)
        OptionToggleViewHolder(view, eventActions)
      }
      else -> throw InvalidKeyException("type is not supported")
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (getItemViewType(position)) {
      TOGGLE -> {
        (holder as OptionToggleViewHolder).bind(getItem(position) as Option.Toggle)
      }
      else -> throw InvalidKeyException("type is not supported")
    }
  }
}

internal class OptionToggleViewHolder(
  view: View,
  private val eventActions: EventActions
) : RecyclerView.ViewHolder(view) {

  private val title: TextView = view.findViewById(R.id.option_title)
  private val container: View = view.findViewById(R.id.option_container)
  private val switch: SwitchMaterial = view.findViewById(R.id.option_switch)

  fun bind(optionToggle: Option.Toggle) {
    title.text = itemView.context.getString(optionToggle.title)
    container.setOnClickListener {
      eventActions.onSettingClick(optionToggle.setting)
    }
    switch.isChecked = optionToggle.active
  }
}

internal object OptionItemCallback : DiffUtil.ItemCallback<Option>() {
  override fun areItemsTheSame(oldItem: Option, newItem: Option): Boolean {
    return oldItem.key == newItem.key
  }

  override fun areContentsTheSame(oldItem: Option, newItem: Option): Boolean {
    return oldItem == newItem
  }

}