package com.guerra.enrico.sera.ui.todos.adapter

import android.content.res.Resources
import android.graphics.Canvas
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.guerra.enrico.base.util.toDateString
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.databinding.ItemTaskBinding
import com.guerra.enrico.sera.ui.todos.EventActions
import com.guerra.enrico.sera.ui.todos.entities.TaskView
import kotlinx.android.synthetic.main.item_task.view.*

/**
 * Created by enrico
 * on 24/06/2018.
 */
class TaskAdapter(
  private val lifecycleOwner: LifecycleOwner,
  private val eventActions: EventActions
) : ListAdapter<TaskView, TaskViewHolder>(TaskDiff) {

  private val recyclerViewCategoriesPool = RecyclerView.RecycledViewPool()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
    val binding =
      ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
        recyclerViewCategories.apply {
          setRecycledViewPool(recyclerViewCategoriesPool)
          layoutManager = FlexboxLayoutManager(context).apply {
            recycleChildrenOnDetach = true
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
            alignItems = AlignItems.FLEX_START
          }
        }
      }

    return TaskViewHolder(binding, lifecycleOwner, eventActions)
  }

  override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
    holder.bind(getItem(position))
  }
}

class TaskViewHolder(
  private val binding: ItemTaskBinding,
  private val lifecycleOwner: LifecycleOwner,
  private val eventActions: EventActions
) : RecyclerView.ViewHolder(binding.root) {

  fun bind(
    taskView: TaskView
  ) {
    binding.taskView = taskView
    binding.lifecycleOwner = lifecycleOwner
    binding.eventActions = eventActions
    binding.executePendingBindings()
  }
}

internal object TaskDiff : DiffUtil.ItemCallback<TaskView>() {
  override fun areItemsTheSame(oldItem: TaskView, newItem: TaskView): Boolean =
    oldItem == newItem

  override fun areContentsTheSame(oldItem: TaskView, newItem: TaskView): Boolean =
    oldItem == newItem

}

class SwipeToDeleteCallback(private val deleteListener: (Int) -> Unit) :
  ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
  override fun onMove(
    recyclerView: RecyclerView,
    viewHolder: RecyclerView.ViewHolder,
    target: RecyclerView.ViewHolder
  ): Boolean {
    return false
  }

  override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    deleteListener(viewHolder.adapterPosition)
  }

  override fun onChildDraw(
    c: Canvas,
    recyclerView: RecyclerView,
    viewHolder: RecyclerView.ViewHolder,
    dX: Float,
    dY: Float,
    actionState: Int,
    isCurrentlyActive: Boolean
  ) {
    super.onChildDraw(c, recyclerView, viewHolder, dX / 2, dY, actionState, isCurrentlyActive)
  }

  override fun onChildDrawOver(
    c: Canvas,
    recyclerView: RecyclerView,
    viewHolder: RecyclerView.ViewHolder?,
    dX: Float,
    dY: Float,
    actionState: Int,
    isCurrentlyActive: Boolean
  ) {
    super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
  }
}