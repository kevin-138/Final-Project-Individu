package com.kevin.netkick.network.service

import com.kevin.netkick.network.NetworkUtils
import com.kevin.netkick.network.model.news.NewsResponseModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsApiService {

    @Headers(NetworkUtils.NEWS_API_KEY)
    @GET("top-headlines")
    suspend fun getNewsHeadline(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("q") q: String
    ): NewsResponseModel

}