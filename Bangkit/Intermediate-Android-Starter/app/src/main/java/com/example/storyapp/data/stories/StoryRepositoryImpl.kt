package com.example.storyapp.data.stories

import androidx.core.net.toFile
import com.example.storyapp.data.base.ApiResponse
import com.example.storyapp.utils.reduceImageFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(private val storyService: StoryService) :
    StoryRepository {

    override fun getStories(): Flow<ApiResponse<StoryResponse>> = flow {
        emit(ApiResponse.Loading)
        try {
            val response = storyService.getStories()
            if (response.error) {
                emit(ApiResponse.Error(response.message))
            } else {
                emit(ApiResponse.Success(response))
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message ?: "Error occurred"))
        }
    }

    override fun uploadStory(uploadStory: UploadStory): Flow<ApiResponse<UploadResponse>> = flow {

        emit(ApiResponse.Loading)
        try {
            val imageFile = uploadStory.uriFile.toFile().reduceImageFile()

            val requestBody = uploadStory.description.toRequestBody("text/plain".toMediaType())
            val requestFileImage = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
                imageFile.name,
                requestFileImage
            )

            val response = storyService.postStories(requestBody, multipartBody)
            if (response.error) {
                emit(ApiResponse.Error(response.message))
            } else {
                emit(ApiResponse.Success(response))
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message ?: "Error occoured"))
        }
    }

    override fun getDetailStory(storyId: String): Flow<ApiResponse<DetailResponse>> = flow {
        emit(ApiResponse.Loading)
        try {
            val response = storyService.getDetail(storyId)
            if (response.error) {
                emit(ApiResponse.Error(response.message))
            } else {
                emit(ApiResponse.Success(response))
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message ?: "Error occoured"))
        }
    }

}