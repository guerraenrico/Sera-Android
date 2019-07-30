package com.guerra.enrico.sera.navigation

import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.ui.goals.GoalsActivity
import com.guerra.enrico.sera.ui.results.ResultsActivity
import com.guerra.enrico.sera.ui.todos.TodosActivity

/**
 * Created by enrico
 * on 27/05/2018.
 */
open class NavigationModel {
  val items = arrayOf(
          NavigationItemEnum.TODOS,
          NavigationItemEnum.GOALS,
          NavigationItemEnum.RESULTS
  )

  enum class NavigationItemEnum(val id: Int, val titleResources: Int, val iconResource: Int, val classToLaunch: Class<*>?, val finishCurrentActivity: Boolean = false) {
    TODOS(R.id.navigation_todo, R.string.title_todos, R.drawable.ic_todo,
            TodosActivity::class.java, true),
    GOALS(R.id.navigation_goals, R.string.title_goals, R.drawable.ic_goals,
            GoalsActivity::class.java, true),
    RESULTS(R.id.navigation_results, R.string.title_results, R.drawable.ic_results,
            ResultsActivity::class.java, true),
    INVALID(0, 0, 0, null);

    object Static {
      fun getById(id: Int): NavigationItemEnum {
        return NavigationItemEnum.values().firstOrNull { it.id == id }
                ?: INVALID
      }
    }

  }
}