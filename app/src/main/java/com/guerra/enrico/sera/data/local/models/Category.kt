package com.guerra.enrico.sera.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by enrico
 * on 02/06/2018.
 */
@Entity(tableName = "Category")
data class Category constructor (
        @PrimaryKey @ColumnInfo(name = "id") var id: String = "",
        @ColumnInfo(name = "name") var name: String = ""
//        @ColumnInfo(name = "isCheched") var isCheched: Boolean = false
)