package com.kevin.netkick.network.service

import com.kevin.netkick.network.NetworkUtils
import com.kevin.netkick.network.model.fixtures.FixturesResponseModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface FootballApiService {

    @Headers(NetworkUtils.FOOTBALL_API_KEY)
    @GET("fixtures")
    suspend fun getLiveMatches(@Query("live") live:String): FixturesResponseModel
}