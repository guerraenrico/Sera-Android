package com.guerra.enrico.todos.models

import android.os.Parcelable
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.models.todos.Suggestion
import kotlinx.android.parcel.Parcelize

/**
 * Created by enrico
 * on 03/05/2020.
 */
@Parcelize
internal data class SearchData(
  val text: String? = null,
  val category: Category? = null,
  val suggestion: Suggestion? = null
) : Parcelable
