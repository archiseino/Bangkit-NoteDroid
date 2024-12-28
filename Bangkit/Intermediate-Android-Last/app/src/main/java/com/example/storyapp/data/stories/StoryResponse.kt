package com.example.storyapp.data.stories

import android.net.Uri
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.storyapp.data.base.BaseResponse
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody

data class UploadStory(
	val description: String,
	val uriFile : Uri,
	val lat: Double = 0.0,
	val lon: Double = 0.0
)

data class StoryResponse(

	@field:SerializedName("listStory")
	val listStory: List<ListStoryItem> = emptyList(),

	) : BaseResponse()

@Entity("story")
data class ListStoryItem(

	@PrimaryKey
	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("photoUrl")
	val photoUrl: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("lon")
	val lon: Double = 0.0,

	@field:SerializedName("lat")
	val lat: Double = 0.0
)

class UploadResponse: BaseResponse()

data class DetailResponse(

	@field:SerializedName("story")
	val story: Story

) : BaseResponse()

data class Story(

	@field:SerializedName("photoUrl")
	val photoUrl: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("lon")
	val lon: Any,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("lat")
	val lat: Any
)
