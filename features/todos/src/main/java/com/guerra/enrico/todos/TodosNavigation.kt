package com.guerra.enrico.todos

import com.guerra.enrico.navigation.models.todos.SearchData
import com.guerra.enrico.navis_annotation.annotations.ActivityRoute
import com.guerra.enrico.navis_annotation.annotations.FragmentRoute
import com.guerra.enrico.navis_annotation.annotations.Result
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
  object List

  @ActivityRoute(TodoAddActivity::class)
  object Add

  @ActivityRoute(TodoSearchActivity::class)
  @Result(SearchData::class)
  object Search

  @FragmentRoute(SelectFragment::class)
  object AddSelect

  @FragmentRoute(AddCategoryFragment::class)
  object AddCategory

  @FragmentRoute(SelectCategoryFragment::class)
  object SelectCategory

  @FragmentRoute(AddTaskFragment::class)
  object AddTask

  @FragmentRoute(ScheduleFragment::class)
  object AddSchedule

  @FragmentRoute(DoneFragment::class)
  object Done
}