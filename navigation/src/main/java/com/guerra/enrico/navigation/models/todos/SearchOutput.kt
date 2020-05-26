package com.guerra.enrico.navigation.models.todos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by enrico
 * on 21/05/2020.
 */
@Parcelize
data class SearchOutput(
  val text: String,
  val categoryId: String? = null,
  val suggestionId: String? = null
) : Parcelable