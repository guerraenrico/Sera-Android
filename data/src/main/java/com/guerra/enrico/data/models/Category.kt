package com.guerra.enrico.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by enrico
 * on 02/06/2018.
 */
@Entity(tableName = "Category")
data class Category constructor (
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "localId") val localId: Long = 0,
        @ColumnInfo(name = "id") val id: String = "",
        @ColumnInfo(name = "name") val name: String = ""
)