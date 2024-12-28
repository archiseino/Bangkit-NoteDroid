package com.example.storyapp.data.stories

import com.example.storyapp.data.base.ApiResponse
import kotlinx.coroutines.flow.Flow

interface StoryRepository {
    fun getStories() : Flow<ApiResponse<StoryResponse>>
    fun uploadStory(uploadStory: UploadStory) : Flow<ApiResponse<UploadResponse>>
    fun getDetailStory(storyId: String) : Flow<ApiResponse<DetailResponse>>
}