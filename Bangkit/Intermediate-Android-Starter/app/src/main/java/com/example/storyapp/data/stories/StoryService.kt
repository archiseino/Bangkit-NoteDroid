package com.example.storyapp.data.stories

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface StoryService {

    @GET("stories")
    suspend fun getStories() : StoryResponse

    @Multipart
    @POST("stories")
    suspend fun postStories(
        @Part("description") description: RequestBody,
        @Part file: MultipartBody.Part
    ) : UploadResponse

    @GET("stories/{storyId}")
    suspend fun getDetail(
        @Path("storyId") storyId: String
    ) : DetailResponse
}