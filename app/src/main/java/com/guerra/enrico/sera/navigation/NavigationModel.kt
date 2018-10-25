package com.guerra.enrico.sera.navigation

import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.ui.charts.ChartsActivity
import com.guerra.enrico.sera.ui.todos.TodosActivity

/**
 * Created by enrico
 * on 27/05/2018.
 */
open class NavigationModel {
    val items = arrayOf(
            NavigationItemEnum.TODOS,
            NavigationItemEnum.CHARTS,
            NavigationItemEnum.NOTIFICATIONS
    )

    enum class NavigationItemEnum(val id: Int, val titleResources: Int, val iconResource: Int, val classToLaunch:  Class<*>?, val finishCurrentActivity: Boolean = false) {
        TODOS(R.id.navigation_todo, R.string.title_todos, R.drawable.ic_todo,
                TodosActivity::class.java, true),
        CHARTS(R.id.navigation_chart, R.string.title_charts, R.drawable.ic_chart,
                ChartsActivity::class.java, true),
        NOTIFICATIONS(R.id.navigation_notifications, R.string.title_notifications, R.drawable.ic_notifications_black_24dp,
                ChartsActivity::class.java, true),
        INVALID(0, 0, 0, null);

        object Static {
            fun getById(id: Int) : NavigationItemEnum {
                return NavigationItemEnum.values().firstOrNull { it.id == id }
                        ?: INVALID
            }
        }

    }
}