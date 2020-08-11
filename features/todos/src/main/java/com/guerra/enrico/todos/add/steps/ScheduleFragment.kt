package com.guerra.enrico.todos.add.steps

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import com.guerra.enrico.base_android.arch.BaseFragment
import com.guerra.enrico.todos.R
import com.guerra.enrico.todos.add.TodoAddViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_todo_add_schedule.*
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
internal class ScheduleFragment : BaseFragment(R.layout.fragment_todo_add_schedule) {

  private val viewModel: TodoAddViewModel  by activityViewModels()

  private var selectedDate = Date()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    scheduleDate.setOnClickListener {
      showDatePicker()
    }
    buttonAdd.setOnClickListener {
      viewModel.onSetTaskSchedule(selectedDate)
    }
  }

  private fun showDatePicker() {
    val today = Calendar.getInstance()
    val year = today.get(Calendar.YEAR)
    val month = today.get(Calendar.MONTH)
    val day = today.get(Calendar.DAY_OF_MONTH)
    val mContext = context ?: return
    val datePickerDialog =
      DatePickerDialog(mContext, OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, selectedYear)
        calendar.set(Calendar.MONTH, selectedMonth)
        calendar.set(Calendar.DAY_OF_MONTH, selectedDay)
        val myFormat = "MM/dd/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.FRANCE)
        scheduleDate.setText(sdf.format(calendar.time))
        selectedDate = calendar.time
      }, year, month, day)
    datePickerDialog.show()
  }
}