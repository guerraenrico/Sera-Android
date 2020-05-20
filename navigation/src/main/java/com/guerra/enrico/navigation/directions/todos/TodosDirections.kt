package com.guerra.enrico.navigation.directions.todos

import com.guerra.enrico.navigation.di.ActivityDestination
import com.guerra.enrico.navigation.di.FragmentDestination
import com.guerra.enrico.navigation.directions.ActivityDirection
import com.guerra.enrico.navigation.directions.FragmentDirection
import com.guerra.enrico.navigation.directions.WithParams
import com.guerra.enrico.navigation.models.todos.TodoSearchParams

/**
 * Created by enrico
 * on 18/05/2020.
 */

object TodosDirections {

  class SearchActivity : ActivityDirection {
    override val destination: ActivityDestination = ActivityDestination.TODOS_SEARCH
  }

  class SearchActivityWithParams(override val params: TodoSearchParams) : ActivityDirection, WithParams<TodoSearchParams>{
    override val key: String = ""
    override val destination: ActivityDestination = ActivityDestination.TODOS_SEARCH
  }

  class SearchFragment : FragmentDirection {
    override val destination: FragmentDestination = FragmentDestination.TODO_SEARCH
  }

}

// nav.todos.search.main.startForResult(activity, bundle) {}
// nav.todos.search.main.start(activity, bundle)

// nav.todos.search.list.replace(fragmentManager, R.id.container, bundle)

// start activity for result: the result of an activity is not directly return with the standard method
// but is saved in a map; the calling activity in the onActivityResultMethod (or maybe even in the on startMethod) look in the map for the value
// the method could look like: fun getResult(code: Int, consume: Boolean) -- the consume param indicate if the value should remain in the map so other activity can read it