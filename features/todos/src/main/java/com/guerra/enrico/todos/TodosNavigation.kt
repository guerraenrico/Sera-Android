package com.guerra.enrico.todos

import com.guerra.enrico.navis_annotation.annotations.ActivityRoute
import com.guerra.enrico.navis_annotation.annotations.FragmentRoute
import com.guerra.enrico.navis_annotation.annotations.Routes
import com.guerra.enrico.todos.add.TodoAddActivity
import com.guerra.enrico.todos.add.steps.AddCategoryFragment
import com.guerra.enrico.todos.add.steps.AddTaskFragment
import com.guerra.enrico.todos.add.steps.DoneFragment
import com.guerra.enrico.todos.add.steps.ScheduleFragment
import com.guerra.enrico.todos.add.steps.SelectCategoryFragment
import com.guerra.enrico.todos.add.steps.SelectFragment
import com.guerra.enrico.todos.search.TodoSearchActivity

/**
 * Created by enrico
 * on 02/06/2020.
 */
@Routes
abstract class TodosNavigation {
  @FragmentRoute(TodosFragment::class)
  object Todos

  @ActivityRoute(TodoAddActivity::class)
  object TodoAdd

  @ActivityRoute(TodoSearchActivity::class)
  object TodoSearch

  @FragmentRoute(SelectFragment::class)
  object TodoAddSelect

  @FragmentRoute(AddCategoryFragment::class)
  object TodoAddCategory

  @FragmentRoute(SelectCategoryFragment::class)
  object TodoAddSelectCategory

  @FragmentRoute(AddTaskFragment::class)
  object TodoAddTask

  @FragmentRoute(ScheduleFragment::class)
  object TodoAddSchedule

  @FragmentRoute(DoneFragment::class)
  object TodoAddDone
}