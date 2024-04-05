package com.example.projectsubmissionandroidfundamentalbangkit.data.remote.retrofit

import com.example.projectsubmissionandroidfundamentalbangkit.data.remote.response.ResponseApi
import com.example.projectsubmissionandroidfundamentalbangkit.data.remote.response.UserDetail
import com.example.projectsubmissionandroidfundamentalbangkit.data.remote.response.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/search/users")
    suspend fun searchProfile(
        @Query("q") userName : String?
    ) : Response<ResponseApi>

    @GET("/users/{username}")
    suspend fun detailProfile(
        @Path("username") userName: String?
    ) : Response<UserDetail>

    @GET("/users/{username}/followers")
    suspend fun userFollower(
        @Path("username") userNam: String?
    ) : Response<List<UserResponse>>

    @GET("/users/{username}/following")
    suspend fun userFollowing(
        @Path("username") userNam: String?
    ) : Response<List<UserResponse>>

}
