package com.guerra.enrico.navigation.models.todos

import android.os.Parcelable
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.models.todos.Suggestion
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchData(
  val text: String? = null,
  val category: Category? = null,
  val suggestion: Suggestion? = null
) : Parcelable