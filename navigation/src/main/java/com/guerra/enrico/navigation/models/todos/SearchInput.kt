package com.guerra.enrico.navigation.models.todos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by enrico
 * on 22/05/2020.
 */
@Parcelize
data class SearchInput(
  val text: String
): Parcelable