package com.guerra.enrico.sera.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by enrico
 * on 16/10/2018.
 */
@Entity(tableName = "Session")
data class Session constructor(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "userId") val userId: String,
    @ColumnInfo(name = "accessToken") val accessToken: String,
    @ColumnInfo(name = "createdAt") val createdAt: Long
)