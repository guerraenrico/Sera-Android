package com.guerra.enrico.remote.request

import com.guerra.enrico.models.todos.Category

data class CategoryParams(
  val id: String,
  val name: String
) {
  constructor(category: Category) : this(category.id, category.name)
}