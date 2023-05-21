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

    override suspend fun getPlayerList(scope: CoroutineScope, team: Int, season: Int): Flow<PagingData<ResponseP>> {
        return repository.getPlayerList(scope,team,season)
    }

    override suspend fun getAllCountries(): Flow<CountryResponse> {
        return repository.getAllCountries()
    }

    override suspend fun getNewsHeadlines(): Flow<NewsResponse> {
        return repository.getNewsHeadlines()
    }

    override suspend fun getLeagueSearch(search: String): Flow<LeagueResponse> {
        return repository.getLeagueSearch(search)
    }

    override suspend fun getLeagueFilterCountry(country: String): Flow<LeagueResponse> {
        return repository.getLeagueFilterCountry(country)
    }

    override suspend fun getLeagueStandings(league: Int, season: Int): Flow<StandingsResponse> {
        return repository.getLeagueStandings(league,season)
    }

    override suspend fun getLeagueTopscore(league: Int, season: Int): Flow<PlayerResponse> {
        return repository.getLeagueTopscore(league,season)
    }


}