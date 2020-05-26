package com.guerra.enrico.navigation.directions.todos

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.guerra.enrico.navigation.TODO_SEARCH_INPUT_KEY
import com.guerra.enrico.navigation.TODO_SEARCH_REQUEST_CODE
import com.guerra.enrico.navigation.TODO_SEARCH_OUTPUT_KEY
import com.guerra.enrico.navigation.di.ActivityTarget
import com.guerra.enrico.navigation.di.FragmentTarget
import com.guerra.enrico.navigation.directions.ActivityDirection
import com.guerra.enrico.navigation.directions.FragmentDirection
import com.guerra.enrico.navigation.directions.Output
import com.guerra.enrico.navigation.models.todos.SearchInput
import com.guerra.enrico.navigation.models.todos.SearchOutput

/**
 * Created by enrico
 * on 18/05/2020.
 */

object TodosDirections {
  object Tasks {
    class Fragment : FragmentDirection {
      override val target: FragmentTarget = FragmentTarget.TODOS
    }
  }

  object Search {
    class Activity : ActivityDirection, Output {
      override val target: ActivityTarget = ActivityTarget.TODOS_SEARCH
      override val code: Int = TODO_SEARCH_REQUEST_CODE
      override val dataKey: String = TODO_SEARCH_OUTPUT_KEY
    }

    class Fragment : FragmentDirection {
      override val target: FragmentTarget = FragmentTarget.TODO_SEARCH
    }
  }

  object Add {
    class Activity : ActivityDirection {
      override val target: ActivityTarget = ActivityTarget.TODOS_ADD
    }

    class SelectFragment : FragmentDirection {
      override val target: FragmentTarget = FragmentTarget.TODO_ADD_SELECT
    }

    class SelectCategoryFragment : FragmentDirection {
      override val target: FragmentTarget = FragmentTarget.TODO_ADD_SELECT_CATEGORY
    }

    class AddCategoryFragment : FragmentDirection {
      override val target: FragmentTarget = FragmentTarget.TODO_ADD_ADD_CATEGORY
    }

    class AddTaskFragment : FragmentDirection {
      override val target: FragmentTarget = FragmentTarget.TODO_ADD_ADD_TASK
    }

    class ScheduleFragment : FragmentDirection {
      override val target: FragmentTarget = FragmentTarget.TODO_ADD_SCHEDULE
    }

    class DoneFragment : FragmentDirection {
      override val target: FragmentTarget = FragmentTarget.TODO_ADD_DONE
    }
  }
}

// TODO Create contract for each direction
class TodosSearchContract(private val clazz: Class<*>): ActivityResultContract<SearchInput, SearchOutput?>() {
  override fun createIntent(context: Context, input: SearchInput?): Intent {
    return Intent(Intent.ACTION_VIEW).setClass(context, clazz).apply {
      putExtra(TODO_SEARCH_INPUT_KEY, input)
    }
  }

  override fun parseResult(resultCode: Int, intent: Intent?): SearchOutput? {
    if (resultCode != Activity.RESULT_OK) {
      return null
    }
    return intent?.getParcelableExtra(TODO_SEARCH_OUTPUT_KEY)
  }
}

// nav.todos.search.main.startForResult(activity, bundle) {}
// nav.todos.search.main.start(activity, bundle)

// nav.todos.search.list.replace(fragmentManager, R.id.container, bundle)

// start activity for result: the result of an activity is not directly return with the standard method
// but is saved in a map; the calling activity in the onActivityResultMethod (or maybe even in the on startMethod) look in the map for the value
// the method could look like: fun getResult(code: Int, consume: Boolean) -- the consume param indicate if the value should remain in the map so other activity can read it