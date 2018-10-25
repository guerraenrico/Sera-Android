package com.guerra.enrico.sera.ui.todos

import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.data.local.models.Task
import kotlinx.android.synthetic.main.item_task.view.*

/**
 * Created by enrico
 * on 24/06/2018.
 */
class TaskAdapter(private val onCompleteClick: (Task, Int) -> Unit): RecyclerView.Adapter<TaskViewHolder>() {
    var tasks = mutableListOf<Task>()

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

class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val toogleExpandTransition: Transition = TransitionInflater.from(itemView.context)
            .inflateTransition(R.transition.task_card_toogle_expand)
    private var expanded = false

    @SuppressLint("RestrictedApi")
    fun bind(task: Task, position: Int, onCompleteClick: (Task, Int) -> Unit, onExpand: () -> Unit) = with(itemView) {
        taskTitle.text = task.title
        taskDescription.text = if(task.description.isEmpty()) resources.getString(R.string.label_no_task_description) else task.description
        buttonComplete.setOnClickListener { onCompleteClick.invoke(task, position) }
        buttonComplete.isChecked = task.completed
        contentTaskTitle.setOnClickListener {
            toogleExpand(containerTaskItem, contentTaskDescription)
            onExpand()
        }
    }

    private fun toogleExpand(viewToExpand: ViewGroup, viewToShow: ViewGroup) {
        expanded = !expanded
        toogleExpandTransition.duration = if (expanded) 300L else 200L
        TransitionManager.beginDelayedTransition(viewToExpand, toogleExpandTransition)
        viewToShow.visibility = if (expanded) View.VISIBLE else View.GONE
    }
}