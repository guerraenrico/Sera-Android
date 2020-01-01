package com.guerra.enrico.sera.ui.todos.entities

import com.guerra.enrico.sera.data.models.Category

/**
 * Created by enrico
 * on 02/09/2018.
 */
data class CategoryView(val category: Category, var isChecked: Boolean = false)