package com.guerra.enrico.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EntityData(
  val id: String = "",
  val name: String = "",
  val snapshot: String = ""
) : Parcelable