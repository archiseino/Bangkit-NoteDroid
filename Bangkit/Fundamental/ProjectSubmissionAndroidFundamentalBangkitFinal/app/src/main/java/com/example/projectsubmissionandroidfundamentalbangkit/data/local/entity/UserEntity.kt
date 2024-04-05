package com.example.projectsubmissionandroidfundamentalbangkit.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity("user")
@Parcelize
data class UserEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    var id: Int = 0,

    @ColumnInfo(name = "login")
    val login: String? = null,

    @ColumnInfo("avatarUrl")
    val avatarUrl: String? = null

) : Parcelable