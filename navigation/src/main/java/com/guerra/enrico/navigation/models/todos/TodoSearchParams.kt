package com.guerra.enrico.navigation.models.todos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by enrico
 * on 19/05/2020.
 */
@Parcelize
data class TodoSearchParams(
  val s: String
): Parcelable