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
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import com.guerra.enrico.base.util.toDateString
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.ui.todos.entities.TaskView
import kotlinx.android.synthetic.main.item_task.view.*

/**
 * Created by enrico
 * on 24/06/2018.
 */
class TaskAdapter(
  private val onCompleteClick: (Task, Int) -> Unit,
  private val onExpandClick: (Task) -> Unit
) :
  RecyclerView.Adapter<TaskViewHolder>() {
  private var tasks = mutableListOf<TaskView>()

  fun updateList(list: List<TaskView>) {
    val diffRes = DiffUtil.calculateDiff(TasksDiffCallback(tasks, list))
    tasks = list.toMutableList()
    diffRes.dispatchUpdatesTo(this)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
    return TaskViewHolder(itemView)
  }

  override fun getItemCount(): Int = tasks.size

  override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
    holder.bind(tasks[position], position, onCompleteClick, onExpandClick)
  }
}

class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  private val toggleExpandTransition: Transition = TransitionInflater.from(itemView.context)
    .inflateTransition(R.transition.task_card_toogle_expand)

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

      toggleExpand(containerTaskItem, contentTaskDescription, taskView.expanded)

      buttonComplete.setOnClickListener { onCompleteClick(taskView.task, position) }
      contentTaskTitle.setOnClickListener { onExpandClick(taskView.task) }
    }

  private fun toggleExpand(viewToExpand: ViewGroup, viewToShow: ViewGroup, expanded: Boolean) {
    toggleExpandTransition.duration = if (expanded) 300L else 200L
    TransitionManager.beginDelayedTransition(viewToExpand, toggleExpandTransition)
    viewToShow.visibility = if (expanded) View.VISIBLE else View.GONE
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

class TasksDiffCallback(
  private val oldList: List<TaskView>,
  private val newList: List<TaskView>
) : DiffUtil.Callback() {
  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
    oldList[oldItemPosition].task.id == newList[newItemPosition].task.id

  override fun getOldListSize(): Int = oldList.size

  override fun getNewListSize(): Int = newList.size

  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    val oldTask = oldList[oldItemPosition]
    val newTask = newList[newItemPosition]
    return oldTask.task.completed == newTask.task.completed && oldTask.expanded == newTask.expanded
  }
}
