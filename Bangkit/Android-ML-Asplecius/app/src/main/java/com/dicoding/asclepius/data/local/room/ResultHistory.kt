package com.dicoding.asclepius.data.local.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("history")
data class ResultHistory (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int = 0,

    @ColumnInfo("imageUri")
    val imageUri : String,

    @ColumnInfo("label")
    val label : String,

    @ColumnInfo("confidenceScore")
    val score : String
)
