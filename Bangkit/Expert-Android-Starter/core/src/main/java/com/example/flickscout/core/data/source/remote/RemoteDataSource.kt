package com.example.flickscout.core.data.source.remote

import android.util.Log
import com.example.flickscout.core.data.source.remote.network.ApiResponse
import com.example.flickscout.core.data.source.remote.network.ApiService
import com.example.flickscout.core.data.source.remote.response.MoviesResponse
import com.example.flickscout.core.data.source.remote.response.ResultsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Dispatcher

class RemoteDataSource (private val apiService: com.example.flickscout.core.data.source.remote.network.ApiService) {

    fun getMovies(): Flow<ApiResponse<List<ResultsItem>>> {
        return flow {
            try {
                val response = apiService.getMovies().results
                if (response.isNotEmpty()){
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}