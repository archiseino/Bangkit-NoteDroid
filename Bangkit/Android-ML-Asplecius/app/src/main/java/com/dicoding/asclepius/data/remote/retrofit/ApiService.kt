package com.dicoding.asclepius.data.remote.retrofit

import com.dicoding.asclepius.data.remote.response.NewsResponse
import com.dicoding.asclepius.utils.NEWS_CATEGORY
import com.dicoding.asclepius.utils.NEWS_COUNTRY
import com.dicoding.asclepius.utils.NEWS_QUERY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("q") query: String = NEWS_QUERY,
        @Query("category") category : String = NEWS_CATEGORY,
        @Query("country") country : String = NEWS_COUNTRY
    ) : Response<NewsResponse>
}