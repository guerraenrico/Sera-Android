package com.guerra.enrico.todos.adapter

import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.guerra.enrico.base.extensions.toDateString
import com.guerra.enrico.base_android.extensions.inflate
import com.guerra.enrico.models.todos.Task
import com.guerra.enrico.todos.R
import kotlin.math.abs

internal class TaskAdapter(
  private val onTaskClick: (Task) -> Unit,
  private val onSwipeToComplete: (Task) -> Unit
) : ListAdapter<Task, TaskViewHolder>(TaskDiff) {

  private val itemTouchHelper = ItemTouchHelper(SwipeToCompleteCallback { position ->
    val task = getItem(position)
    onSwipeToComplete(task)
  })

  override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
    super.onAttachedToRecyclerView(recyclerView)
    itemTouchHelper.attachToRecyclerView(recyclerView)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
    val view = parent.inflate(R.layout.item_task)
    return TaskViewHolder(view)
  }

  override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
    holder.bind(getItem(position), onTaskClick)
  }
}

internal class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {

  private val container: View = view.findViewById(R.id.containerTaskItem)
  private val title: TextView = view.findViewById(R.id.taskTitle)
  private val date: TextView = view.findViewById(R.id.taskDate)
  private val description: TextView = view.findViewById(R.id.taskDescription)
  private val recyclerViewCategories: RecyclerView =
    view.findViewById(R.id.recyclerViewCategories)

  private val dateFormatText = view.context.getString(R.string.label_todo_within)

  init {
    recyclerViewCategories.apply {
      layoutManager = FlexboxLayoutManager(context).apply {
        recycleChildrenOnDetach = true
        flexDirection = FlexDirection.ROW
        justifyContent = JustifyContent.FLEX_START
        alignItems = AlignItems.FLEX_START
      }
    }
  }

  fun bind(task: Task, onClick: (Task) -> Unit) {
    container.setOnClickListener { onClick(task) }

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

internal object TaskDiff : DiffUtil.ItemCallback<Task>() {
  override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean =
    oldItem.id == newItem.id

  override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean =
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
      .onSelected(viewHolder?.itemView?.findViewById(R.id.containerTaskItem))
  }

  override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
    ItemTouchHelper.Callback.getDefaultUIUtil()
      .clearView(viewHolder.itemView.findViewById(R.id.containerTaskItem))
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
      viewHolder.itemView.findViewById(R.id.containerTaskItem),
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
      viewHolder?.itemView?.findViewById(R.id.containerTaskItem),
      dX,
      dY,
      actionState,
      isCurrentlyActive
    )
  }
}

