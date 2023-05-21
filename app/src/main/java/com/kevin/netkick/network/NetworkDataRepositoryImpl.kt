package com.kevin.netkick.network

import TeamResponseModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kevin.netkick.domain.DomainRepository
import com.kevin.netkick.domain.entity.country.CountryResponse
import com.kevin.netkick.domain.entity.fixtures.FixturesResponse
import com.kevin.netkick.domain.entity.league.LeagueResponse
import com.kevin.netkick.domain.entity.news.NewsResponse
import com.kevin.netkick.domain.entity.player.PlayerResponse
import com.kevin.netkick.domain.entity.player.ResponseP
import com.kevin.netkick.domain.entity.standings.StandingsResponse
import com.kevin.netkick.domain.entity.teams.TeamResponse
import com.kevin.netkick.network.model.countries.CountryResponseModel
import com.kevin.netkick.network.model.fixtures.FixturesResponseModel
import com.kevin.netkick.network.model.league.LeagueResponseModel
import com.kevin.netkick.network.model.news.NewsResponseModel
import com.kevin.netkick.network.model.player.PlayerResponseModel
import com.kevin.netkick.network.model.standings.StandingsResponseModel
import com.kevin.netkick.network.paging.PlayersPagingDataSource
import com.kevin.netkick.network.service.FootballApiService
import com.kevin.netkick.network.service.NewsApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NetworkDataRepositoryImpl @Inject constructor(private val footballApi:FootballApiService, private val newsApi:NewsApiService):DomainRepository {

    override suspend fun getLiveMatches(live: String): Flow<FixturesResponse> {
        return flow {
        try {
//           val response =
           emit(FixturesResponseModel.transformToEntity(footballApi.getLiveMatches(live)))
        } catch (e: Exception){
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)
}

    override suspend fun getPopularTeamsHome(league: Int, season: Int): Flow<TeamResponse> {
        return flow {
            try {
                val response = footballApi.getPopularTeamsHome(league,season)
                emit(TeamResponseModel.transfromToEntity(response))
            } catch (e: Exception){
                e.printStackTrace()
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getAllCountries(): Flow<CountryResponse> {
        return flow {
            try {
                val response = footballApi.getAllCountries()
                emit(CountryResponseModel.transformToEntity(response))
            }catch (e:Exception){
                e.printStackTrace()
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getNewsHeadlines(): Flow<NewsResponse> {
        return flow {
            try {
                val response = newsApi.getNewsHeadline(NetworkUtils.NEWS_COUNTRY,NetworkUtils.NEWS_CATEGORY,NetworkUtils.NEWS_Q)
                emit(NewsResponseModel.transformsToEntity(response))
            } catch (e: Exception){
                e.printStackTrace()
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getTeamDetail(id: Int): Flow<TeamResponse> {
        return flow {
            try {
                val response = footballApi.getTeamsDetail(id)
                emit(TeamResponseModel.transfromToEntity(response))
            } catch (e: Exception){
                e.printStackTrace()
                }
            }.flowOn(Dispatchers.IO)
    }

    override suspend fun getPlayerList(scope:CoroutineScope, team: Int, season: Int): Flow<PagingData<ResponseP>> {
        return Pager(config = PagingConfig(pageSize = 10)
        ) {
            PlayersPagingDataSource(footballApi, team = team, season = season)
        }.flow.cachedIn(scope)
    }

    override suspend fun getLeagueSearch(search: String): Flow<LeagueResponse> {
       return flow {
           try {
               val response = footballApi.getLeagueSearch(search)
               emit(LeagueResponseModel.transformToEntity(response))
           } catch (e: Exception){
               e.printStackTrace()
           }
       }.flowOn(Dispatchers.IO)
       }

    override suspend fun getLeagueFilterCountry(country: String): Flow<LeagueResponse> {
        return flow {
            try {
                val response = footballApi.getLeagueFilterCountry(country)
                emit(LeagueResponseModel.transformToEntity(response))
            } catch (e: Exception){
                e.printStackTrace()
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getLeagueStandings(league: Int, season: Int): Flow<StandingsResponse> {
        return flow {
            try {
                val response = footballApi.getLeagueStandings(league,season)
                emit(StandingsResponseModel.transformToEntity(response))
            } catch (e: Exception){
                e.printStackTrace()
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getLeagueTopscore(league: Int, season: Int): Flow<PlayerResponse> {
        return flow {
            try {
                val response = footballApi.getLeagueTopscore(league,season)
                emit(PlayerResponseModel.transformToEntity(response))
            } catch (e: Exception){
                e.printStackTrace()
            }
        }.flowOn(Dispatchers.IO)
    }
}





