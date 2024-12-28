package com.example.storyapp.data.auth

import com.example.storyapp.data.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class RequestLogin(
    val email: String,
    val password: String
)

data class RequestRegister(
    val name: String,
    val email: String,
    val password: String
)

data class LoginResponse (

    @field:SerializedName("loginResult")
    val loginResult: LoginResult

) : BaseResponse()

data class LoginResult(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("token")
    val token: String
)

class RegisterResponse : BaseResponse()

