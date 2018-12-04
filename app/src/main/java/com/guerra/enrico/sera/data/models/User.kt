package com.guerra.enrico.sera.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by enrico
 * on 14/10/2018.
 */
@Entity(tableName = "User")
data class User constructor(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "googleId") val googleId: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name =  "name") val name: String,
    @ColumnInfo(name = "locale") val locale: String,
    @ColumnInfo(name = "pictureUrl") val pictureUrl: String
)