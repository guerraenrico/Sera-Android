package com.guerra.enrico.sera.ui.todos

import androidx.databinding.ObservableBoolean
import com.guerra.enrico.sera.data.local.models.Category

/**
 * Created by enrico
 * on 02/09/2018.
 */
 class CategoryFilter(val category: Category, isChecked: Boolean = false) {
    var isChecked = ObservableBoolean(isChecked)
}