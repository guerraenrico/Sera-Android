package com.guerra.enrico.sera.ui.todos.filter

import com.guerra.enrico.sera.data.models.Category

/**
 * Created by enrico
 * on 26/08/2018.
 */
class CategoryFilterManager {
    val selectedCategoriesFilter: MutableList<Category> = mutableListOf()

    @Synchronized
    fun addAll(list: List<Category>) {
        selectedCategoriesFilter.addAll(list)
    }

    @Synchronized
    fun add(category: Category) {
        selectedCategoriesFilter.add(category)
    }

    @Synchronized
    fun remove(category: Category) {
        selectedCategoriesFilter.remove(category)
    }

    @Synchronized
    fun replace(category: Category) {
        selectedCategoriesFilter.map { if (it.id == category.id) category else it }
    }

    // TODO add save and load previus selected categories
}