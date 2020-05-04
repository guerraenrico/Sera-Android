package com.guerra.enrico.sera.ui.todos.models

import android.os.Parcelable
import com.guerra.enrico.models.todos.Category
import kotlinx.android.parcel.Parcelize

/**
 * Created by enrico
 * on 03/05/2020.
 */
@Parcelize
data class SearchData(
  val text: String? = null,
  val category: Category? = null
) : Parcelable
