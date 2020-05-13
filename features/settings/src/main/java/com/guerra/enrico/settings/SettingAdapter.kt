package com.guerra.enrico.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.guerra.enrico.settings.databinding.ItemSettingOptionToggleBinding
import com.guerra.enrico.settings.presentation.Option
import java.security.InvalidKeyException

/**
 * Created by enrico
 * on 09/03/2020.
 */
internal class SettingAdapter(
  private val lifecycleOwner: LifecycleOwner,
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
        val binding =
          ItemSettingOptionToggleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        OptionToggleViewHolder(
          parent.context,
          binding,
          lifecycleOwner,
          eventActions
        )
      }
      else -> throw InvalidKeyException("type is no supported")
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (getItemViewType(position)) {
      TOGGLE -> {
        (holder as OptionToggleViewHolder).bind(getItem(position) as Option.Toggle)
      }
      else -> throw InvalidKeyException("type is no supported")
    }
  }
}

internal class OptionToggleViewHolder(
  private val context: Context,
  private val binding: ItemSettingOptionToggleBinding,
  private val lifecycleOwner: LifecycleOwner,
  private val eventActions: EventActions
) : RecyclerView.ViewHolder(binding.root) {
  fun bind(optionToggle: Option.Toggle) {
    binding.optionTitle.text = context.getString(optionToggle.title)
    binding.lifecycleOwner = lifecycleOwner
    binding.optionContainer.setOnClickListener {
      eventActions.onSettingClick(optionToggle.setting)
    }
    binding.optionSwitch.isChecked = optionToggle.active
    binding.executePendingBindings()
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