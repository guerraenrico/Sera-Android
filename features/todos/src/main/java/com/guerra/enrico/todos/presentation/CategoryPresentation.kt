package com.guerra.enrico.todos.presentation

import com.guerra.enrico.models.todos.Category

/**
 * Created by enrico
 * on 02/09/2018.
 */
internal data class CategoryPresentation(val category: Category, var isChecked: Boolean = false)