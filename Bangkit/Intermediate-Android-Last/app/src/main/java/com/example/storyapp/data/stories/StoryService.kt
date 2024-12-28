package com.example.storyapp.data.stories

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface StoryService {

    @GET("stories")
    suspend fun getStoriesWithMap(
        @Query("location") location : Int = 1
    ) : StoryResponse

    @Multipart
    @POST("stories")
    suspend fun postStories(
        @Part("description") description: RequestBody,
        @Part file: MultipartBody.Part,
        @Part("lon") lon : Float,
        @Part("lat") lat : Float
    ) : UploadResponse

    @GET("stories/{storyId}")
    suspend fun getDetail(
        @Path("storyId") storyId: String
    ) : DetailResponse

    @GET("stories")
    suspend fun getStories(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10
    ) : StoryResponse
}