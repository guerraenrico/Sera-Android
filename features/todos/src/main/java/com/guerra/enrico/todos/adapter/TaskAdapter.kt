package com.guerra.enrico.todos.adapter

import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.guerra.enrico.base.extensions.inflate
import com.guerra.enrico.base.extensions.toDateString
import com.guerra.enrico.todos.EventActions
import com.guerra.enrico.todos.R
import com.guerra.enrico.todos.presentation.TaskPresentation
import kotlin.math.abs

/**
 * Created by enrico
 * on 24/06/2018.
 */
internal class TaskAdapter(
  private val eventActions: EventActions
) : ListAdapter<TaskPresentation, TaskViewHolder>(TaskDiff) {

  private val recyclerViewCategoriesPool = RecyclerView.RecycledViewPool()
  private val itemTouchHelper = ItemTouchHelper(SwipeToCompleteCallback {
    eventActions.onTaskSwipeToComplete(it)
  })

  override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
    super.onAttachedToRecyclerView(recyclerView)
    itemTouchHelper.attachToRecyclerView(recyclerView)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
    val view = parent.inflate(R.layout.item_task)
    return TaskViewHolder(view, recyclerViewCategoriesPool, eventActions)
  }

  override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
    holder.bind(getItem(position))
  }
}

internal class TaskViewHolder(
  view: View,
  recyclerViewPool: RecyclerView.RecycledViewPool,
  private val eventActions: EventActions
) : RecyclerView.ViewHolder(view) {

  private val container: View = view.findViewById(R.id.container_task_item)
  private val title: TextView = view.findViewById(R.id.task_title)
  private val date: TextView = view.findViewById(R.id.task_date)
  private val description: TextView = view.findViewById(R.id.task_description)
  private val recyclerViewCategories: RecyclerView =
    view.findViewById(R.id.recycler_view_categories)

  private val dateFormatText = view.context.getString(R.string.label_todo_within)

  init {
    recyclerViewCategories.apply {
      setRecycledViewPool(recyclerViewPool)
      layoutManager = FlexboxLayoutManager(context).apply {
        recycleChildrenOnDetach = true
        flexDirection = FlexDirection.ROW
        justifyContent = JustifyContent.FLEX_START
        alignItems = AlignItems.FLEX_START
      }
    }
  }

  fun bind(taskPresentation: TaskPresentation) {
    val task = taskPresentation.task
    container.setOnClickListener { eventActions.onTaskClick(task) }

    title.text = task.title

    if (task.description.isBlank()) {
      description.isVisible = false
    } else {
      description.isVisible = true
      description.text = task.description
    }

    date.text = String.format(dateFormatText, task.todoWithin.toDateString())

    if (task.categories.isEmpty()) {
      recyclerViewCategories.isVisible = false
    } else {
      recyclerViewCategories.isVisible = true
      val adapter = recyclerViewCategories.adapter as? SimpleCategoryAdapter
        ?: SimpleCategoryAdapter()
      recyclerViewCategories.adapter = adapter.apply {
        categories = task.categories
      }
    }
  }
}

internal object TaskDiff : DiffUtil.ItemCallback<TaskPresentation>() {
  override fun areItemsTheSame(oldItem: TaskPresentation, newItem: TaskPresentation): Boolean =
    oldItem.task.id == newItem.task.id

  override fun areContentsTheSame(oldItem: TaskPresentation, newItem: TaskPresentation): Boolean =
    oldItem == newItem
}

internal class SwipeToCompleteCallback(private val completeListener: (Int) -> Unit) :
  ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

  override fun onMove(
    recyclerView: RecyclerView,
    viewHolder: RecyclerView.ViewHolder,
    target: RecyclerView.ViewHolder
  ): Boolean {
    return false
  }

  override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    if (direction == ItemTouchHelper.LEFT) {
      completeListener(viewHolder.adapterPosition)
    }
  }

  override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
    ItemTouchHelper.Callback.getDefaultUIUtil()
      .onSelected(viewHolder?.itemView?.findViewById(R.id.container_task_item))
  }

  override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
    ItemTouchHelper.Callback.getDefaultUIUtil()
      .clearView(viewHolder.itemView.findViewById(R.id.container_task_item))
  }

  override fun onChildDraw(
    canvas: Canvas,
    recyclerView: RecyclerView,
    viewHolder: RecyclerView.ViewHolder,
    dX: Float,
    dY: Float,
    actionState: Int,
    isCurrentlyActive: Boolean
  ) {
    val backdrop = viewHolder.itemView.findViewById<MotionLayout>(R.id.backdrop)
    backdrop.progress = abs(dX / canvas.width)

    ItemTouchHelper.Callback.getDefaultUIUtil().onDraw(
      canvas,
      recyclerView,
      viewHolder.itemView.findViewById(R.id.container_task_item),
      dX,
      dY,
      actionState,
      isCurrentlyActive
    )
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
    ItemTouchHelper.Callback.getDefaultUIUtil().onDrawOver(
      c,
      recyclerView,
      viewHolder?.itemView?.findViewById(R.id.container_task_item),
      dX,
      dY,
      actionState,
      isCurrentlyActive
    )
  }
}

