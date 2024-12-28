package com.example.project_submission_android_bangkit.adapter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Driver(
    val driverImage : Int,
    val driverNationalityFlag : Int,
    val driverNationality : String,
    val driverName : String,
    val driverNumber : String,
    val driverTeam : String,
    val driverPodium : String,
    val driverDoB : String,
    val driverPoB : String,
    val driverShortDesc : String,
    val driverLongDesc : String,
) : Parcelable
