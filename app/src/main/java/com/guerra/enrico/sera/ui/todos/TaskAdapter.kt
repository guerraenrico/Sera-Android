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
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.util.toDateString
import kotlinx.android.synthetic.main.item_task.view.*

/**
 * Created by enrico
 * on 24/06/2018.
 */
class TaskAdapter(private val onCompleteClick: (Task, Int) -> Unit) : RecyclerView.Adapter<TaskViewHolder>() {
  private var tasks = mutableListOf<Task>()

  fun updateList(list: List<Task>) {
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
    holder.bind(tasks[position], position, onCompleteClick) {
      notifyItemChanged(position)
    }
  }
}

class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  private val toogleExpandTransition: Transition = TransitionInflater.from(itemView.context)
          .inflateTransition(R.transition.task_card_toogle_expand)
  private var expanded = false

  @SuppressLint("RestrictedApi")
  fun bind(task: Task, position: Int, onCompleteClick: (Task, Int) -> Unit, onExpand: () -> Unit) = with(itemView) {
    taskTitle.text = task.title
    taskDescription.text = if (task.description.isEmpty()) resources.getString(R.string.label_no_task_description) else task.description
    taskDate.text = String.format(
            resources.getString(R.string.label_todo_within),
            task.todoWithin.toDateString()
    )

    taskTitle.paintFlags = if (task.completed) Paint.STRIKE_THRU_TEXT_FLAG else taskTitle.paintFlags
    setViewColors(resources, task.completed, taskTitle, taskDate, taskDescription)
    buttonComplete.isChecked = task.completed

    buttonComplete.setOnClickListener { onCompleteClick.invoke(task, position) }
    contentTaskTitle.setOnClickListener {
      toggleExpand(containerTaskItem, contentTaskDescription)
      onExpand()
    }
  }

  private fun toggleExpand(viewToExpand: ViewGroup, viewToShow: ViewGroup) {
    expanded = !expanded
    toogleExpandTransition.duration = if (expanded) 300L else 200L
    TransitionManager.beginDelayedTransition(viewToExpand, toogleExpandTransition)
    viewToShow.visibility = if (expanded) View.VISIBLE else View.GONE
  }

  private fun setViewColors(resources: Resources, completed: Boolean, vararg views: AppCompatTextView) {
    views.forEach { view ->
      if (completed) {
        view.setTextColor(resources.getColor(R.color.task_list_item_color_completed))
      } else {
        view.setTextColor(resources.getColor(R.color.task_list_item_color_active))
      }
    }
  }
}

class TasksDiffCallback(
        private val oldList: List<Task>,
        private val newList: List<Task>
) : DiffUtil.Callback() {
  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
          oldList[oldItemPosition].id == newList[newItemPosition].id

  override fun getOldListSize(): Int = oldList.size

  override fun getNewListSize(): Int = newList.size

  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
          oldList[oldItemPosition].completed == newList[newItemPosition].completed
}
