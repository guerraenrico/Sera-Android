package com.guerra.enrico.data.remote.request

import com.guerra.enrico.data.models.Category


/**
 * Created by enrico
 * on 17/10/2018.
 */
data class CategoryParams(
        val id: String,
        val name: String
) {
  constructor(category: Category) : this(category.id, category.name)
}