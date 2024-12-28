package com.example.storyapp.data.base

import com.google.gson.annotations.SerializedName

abstract class BaseResponse {
    @field:SerializedName("error")
    val error: Boolean = false

    @field:SerializedName("message")
    val message: String = ""
}