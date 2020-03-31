package com.guerra.enrico.sera.ui.todos.presentation

import com.guerra.enrico.models.todos.Category

/**
 * Created by enrico
 * on 02/09/2018.
 */
data class CategoryPresentation(val category: Category, var isChecked: Boolean = false)