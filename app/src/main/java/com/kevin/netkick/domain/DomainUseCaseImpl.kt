package com.kevin.netkick.domain

import com.kevin.netkick.domain.entity.country.CountryResponse
import com.kevin.netkick.domain.entity.fixtures.FixturesResponse
import com.kevin.netkick.domain.entity.news.NewsResponse
import com.kevin.netkick.domain.entity.player.PlayerResponse
import com.kevin.netkick.domain.entity.teams.TeamResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DomainUseCaseImpl @Inject constructor(private val repository: DomainRepository):DomainUseCase  {
    override suspend fun getLiveMatches(live: String): Flow<FixturesResponse> {
        return repository.getLiveMatches(live)
    }

    override suspend fun getPopularTeamsHome(league: Int, season: Int): Flow<TeamResponse> {
        return repository.getPopularTeamsHome(league,season)
    }

    override suspend fun getTeamDetail(id: Int): Flow<TeamResponse> {
        return repository.getTeamDetail(id)
    }

    override suspend fun getPlayerList(team: Int, season: Int): Flow<PlayerResponse> {
        return repository.getPlayerList(team,season)
    }

    override suspend fun getAllCountries(): Flow<CountryResponse> {
        return repository.getAllCountries()
    }

    override suspend fun getNewsHeadlines(): Flow<NewsResponse> {
        return repository.getNewsHeadlines()
    }

}