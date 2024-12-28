package com.example.storyapp.data.stories

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.storyapp.data.base.ApiResponse
import kotlinx.coroutines.flow.Flow

interface StoryRepository {
    fun getStoriesWithMap() : Flow<ApiResponse<StoryResponse>>
    fun getStories() : LiveData<PagingData<ListStoryItem>>
    fun uploadStory(uploadStory: UploadStory) : Flow<ApiResponse<UploadResponse>>
    fun getDetailStory(storyId: String) : Flow<ApiResponse<DetailResponse>>
}