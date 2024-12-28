package com.example.storyapp.data.stories

import androidx.core.net.toFile
import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.storyapp.data.base.ApiResponse
import com.example.storyapp.data.local.AppDatabase
import com.example.storyapp.utils.reduceImageFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(private val appDatabase: AppDatabase, private val storyService: StoryService) :
    StoryRepository {

    override fun getStories(): LiveData<PagingData<ListStoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyService, appDatabase),
            pagingSourceFactory = {
                appDatabase.storyDao().getStories()
            }
        ).liveData
    }

    override fun getStoriesWithMap(): Flow<ApiResponse<StoryResponse>> = flow {
        emit(ApiResponse.Loading)
        try {
            val response = storyService.getStoriesWithMap()
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

            val response = storyService.postStories(requestBody, multipartBody, uploadStory.lat.toFloat(), uploadStory.lon.toFloat())
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