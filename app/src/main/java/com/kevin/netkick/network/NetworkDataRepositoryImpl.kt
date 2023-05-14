package com.kevin.netkick.network

import TeamResponseModel
import com.kevin.netkick.domain.DomainRepository
import com.kevin.netkick.domain.entity.country.CountryResponse
import com.kevin.netkick.domain.entity.fixtures.FixturesResponse
import com.kevin.netkick.domain.entity.news.NewsResponse
import com.kevin.netkick.domain.entity.player.PlayerResponse
import com.kevin.netkick.domain.entity.teams.TeamResponse
import com.kevin.netkick.network.model.countries.CountryResponseModel
import com.kevin.netkick.network.model.fixtures.FixturesResponseModel
import com.kevin.netkick.network.model.news.NewsResponseModel
import com.kevin.netkick.network.service.FootballApiService
import com.kevin.netkick.network.service.NewsApiService
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
        TODO("Not yet implemented")
    }

    override suspend fun getPlayerList(team: Int, season: Int): Flow<PlayerResponse> {
        TODO("Not yet implemented")
    }


}