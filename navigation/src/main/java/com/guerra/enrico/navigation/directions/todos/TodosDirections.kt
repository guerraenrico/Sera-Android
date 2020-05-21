package com.guerra.enrico.navigation.directions.todos

import com.guerra.enrico.navigation.TODO_SEARCH_REQUEST_CODE
import com.guerra.enrico.navigation.di.ActivityDestination
import com.guerra.enrico.navigation.di.FragmentDestination
import com.guerra.enrico.navigation.directions.ActivityDirection
import com.guerra.enrico.navigation.directions.FragmentDirection
import com.guerra.enrico.navigation.directions.WithResult

/**
 * Created by enrico
 * on 18/05/2020.
 */

object TodosDirections {
  object Tasks {
    class Fragment : FragmentDirection {
      override val destination: FragmentDestination = FragmentDestination.TODOS
    }
  }

  object Search {
    class Activity : ActivityDirection, WithResult {
      override val destination: ActivityDestination = ActivityDestination.TODOS_SEARCH
      override val code: Int = TODO_SEARCH_REQUEST_CODE
    }

    class Fragment : FragmentDirection {
      override val destination: FragmentDestination = FragmentDestination.TODO_SEARCH
    }
  }

  object Add {
    class Activity : ActivityDirection {
      override val destination: ActivityDestination = ActivityDestination.TODOS_ADD
    }

    class SelectFragment : FragmentDirection {
      override val destination: FragmentDestination = FragmentDestination.TODO_ADD_SELECT
    }

    class SelectCategoryFragment : FragmentDirection {
      override val destination: FragmentDestination = FragmentDestination.TODO_ADD_SELECT_CATEGORY
    }

    class AddCategoryFragment : FragmentDirection {
      override val destination: FragmentDestination = FragmentDestination.TODO_ADD_ADD_CATEGORY
    }

    class AddTaskFragment : FragmentDirection {
      override val destination: FragmentDestination = FragmentDestination.TODO_ADD_ADD_TASK
    }

    class ScheduleFragment : FragmentDirection {
      override val destination: FragmentDestination = FragmentDestination.TODO_ADD_SCHEDULE
    }

    class DoneFragment : FragmentDirection {
      override val destination: FragmentDestination = FragmentDestination.TODO_ADD_DONE
    }
  }
}

// nav.todos.search.main.startForResult(activity, bundle) {}
// nav.todos.search.main.start(activity, bundle)

// nav.todos.search.list.replace(fragmentManager, R.id.container, bundle)

// start activity for result: the result of an activity is not directly return with the standard method
// but is saved in a map; the calling activity in the onActivityResultMethod (or maybe even in the on startMethod) look in the map for the value
// the method could look like: fun getResult(code: Int, consume: Boolean) -- the consume param indicate if the value should remain in the map so other activity can read it