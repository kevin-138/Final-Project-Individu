package com.kevin.netkick.network.service


import TeamResponseModel
import com.kevin.netkick.network.NetworkUtils
import com.kevin.netkick.network.model.countries.CountryResponseModel
import com.kevin.netkick.network.model.fixtures.FixturesResponseModel
import com.kevin.netkick.network.model.league.LeagueResponseModel
import com.kevin.netkick.network.model.player.PlayerResponseModel
import com.kevin.netkick.network.model.standings.StandingsResponseModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface FootballApiService {

    @Headers(NetworkUtils.FOOTBALL_API_KEY)
    @GET("fixtures")
    suspend fun getLiveMatches(@Query("live") live:String): FixturesResponseModel

    @Headers(NetworkUtils.FOOTBALL_API_KEY)
    @GET("teams")
    suspend fun getPopularTeamsHome(@Query("league") league:Int,@Query("season") season:Int): TeamResponseModel

    @Headers(NetworkUtils.FOOTBALL_API_KEY)
    @GET("countries")
    suspend fun getAllCountries(): CountryResponseModel

    @Headers(NetworkUtils.FOOTBALL_API_KEY)
    @GET("teams")
    suspend fun getTeamsDetail(@Query("id") id:Int): TeamResponseModel

    @Headers(NetworkUtils.FOOTBALL_API_KEY)
    @GET("players")
    suspend fun getPlayerInTeams(@Query("team") team:Int,@Query("season") season: Int, @Query("page") page: Int): PlayerResponseModel

    @Headers(NetworkUtils.FOOTBALL_API_KEY)
    @GET("leagues")
    suspend fun getLeagueSearch(@Query("search") search:String): LeagueResponseModel

    @Headers(NetworkUtils.FOOTBALL_API_KEY)
    @GET("leagues")
    suspend fun getLeagueFilterCountry(@Query("country") country:String): LeagueResponseModel

    @Headers(NetworkUtils.FOOTBALL_API_KEY)
    @GET("standings")
    suspend fun getLeagueStandings(@Query("league") league:Int,@Query("season") season:Int): StandingsResponseModel

    @Headers(NetworkUtils.FOOTBALL_API_KEY)
    @GET("players/topscorers")
    suspend fun getLeagueTopscore(@Query("league") league:Int,@Query("season") season:Int): PlayerResponseModel

}