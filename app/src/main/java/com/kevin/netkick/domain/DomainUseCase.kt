package com.kevin.netkick.domain

import androidx.paging.PagingData
import com.kevin.netkick.domain.entity.country.CountryResponse
import com.kevin.netkick.domain.entity.fixtures.FixturesResponse
import com.kevin.netkick.domain.entity.league.LeagueResponse
import com.kevin.netkick.domain.entity.news.NewsResponse
import com.kevin.netkick.domain.entity.player.PlayerResponse
import com.kevin.netkick.domain.entity.player.ResponseP
import com.kevin.netkick.domain.entity.standings.StandingsResponse
import com.kevin.netkick.domain.entity.teams.TeamResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface DomainUseCase {
    suspend fun getLiveMatches(live:String): Flow<FixturesResponse>
    suspend fun getPopularTeamsHome(league:Int, season:Int): Flow<TeamResponse>
    suspend fun getTeamDetail(id:Int): Flow<TeamResponse>
    suspend fun getPlayerList(scope: CoroutineScope,team: Int, season:Int):Flow<PagingData<ResponseP>>
    suspend fun getAllCountries():Flow<CountryResponse>
    suspend fun getNewsHeadlines(): Flow<NewsResponse>
    suspend fun getLeagueSearch(search:String): Flow<LeagueResponse>
    suspend fun getLeagueFilterCountry(country:String): Flow<LeagueResponse>
    suspend fun getLeagueStandings(league: Int, season:Int): Flow<StandingsResponse>
}