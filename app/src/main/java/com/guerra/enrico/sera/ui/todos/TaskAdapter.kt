package com.guerra.enrico.sera.ui.todos

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Paint
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import com.guerra.enrico.base.util.toDateString
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.ui.todos.entities.TaskView
import kotlinx.android.synthetic.main.item_task.view.*
import kotlin.math.exp

/**
 * Created by enrico
 * on 24/06/2018.
 */
class TaskAdapter(
  private val onCompleteClick: (Task, Int) -> Unit,
  private val onExpandClick: (Task) -> Unit
) : ListAdapter<TaskView, TaskViewHolder>(TaskDiff) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
    return TaskViewHolder(itemView)
  }

  override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
    holder.bind(getItem(position), position, onCompleteClick, onExpandClick)
  }
}

class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  @SuppressLint("RestrictedApi")
  fun bind(
    taskView: TaskView,
    position: Int,
    onCompleteClick: (Task, Int) -> Unit,
    onExpandClick: (Task) -> Unit
  ) =
    with(itemView) {
      taskTitle.text = taskView.task.title
      taskDescription.text =
        if (taskView.task.description.isEmpty()) resources.getString(R.string.label_no_task_description) else taskView.task.description
      taskDate.text = String.format(
        resources.getString(R.string.label_todo_within),
        taskView.task.todoWithin.toDateString()
      )

      taskTitle.paintFlags =
        if (taskView.task.completed) Paint.STRIKE_THRU_TEXT_FLAG else taskTitle.paintFlags
      setViewColors(resources, taskView.task.completed, taskTitle, taskDate, taskDescription)
      buttonComplete.isChecked = taskView.task.completed

      contentTaskDescription.visibility = if (taskView.expanded) View.VISIBLE else View.GONE

      buttonComplete.setOnClickListener { onCompleteClick(taskView.task, position) }
      contentTaskTitle.setOnClickListener { onExpandClick(taskView.task) }
    }

  private fun setViewColors(
    resources: Resources,
    completed: Boolean,
    vararg views: AppCompatTextView
  ) {
    views.forEach { view ->
      view.setTextColor(
        if (completed) {
          resources.getColor(R.color.task_list_item_color_completed)
        } else {
          resources.getColor(R.color.task_list_item_color_active)
        }
      )
    }
  }
}

object TaskDiff : DiffUtil.ItemCallback<TaskView>() {
  override fun areItemsTheSame(oldItem: TaskView, newItem: TaskView): Boolean =
    oldItem.task.id == newItem.task.id

  override fun areContentsTheSame(oldItem: TaskView, newItem: TaskView): Boolean =
    oldItem.task.completed == newItem.task.completed && oldItem.expanded == newItem.expanded
}