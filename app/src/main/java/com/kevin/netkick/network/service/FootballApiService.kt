package com.kevin.netkick.network.service

import com.kevin.netkick.network.model.fixtures.FixturesResponseModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface FootballApiService {

    @Headers("x-apisports-key: c544dcdd442a1c5159c476243f1299d8")
    @GET("fixtures")
    suspend fun getLiveMatches(@Query("live") live:String): FixturesResponseModel
}