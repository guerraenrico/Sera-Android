package com.guerra.enrico.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by enrico
 * on 01/05/2020.
 */
@Parcelize
data class EntityData(
  val id: String = "",
  val name: String = "",
  val snapshot: String = ""
) : Parcelable