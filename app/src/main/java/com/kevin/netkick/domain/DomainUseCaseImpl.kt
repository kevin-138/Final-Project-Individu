package com.kevin.netkick.domain

import androidx.paging.PagingData
import com.kevin.netkick.domain.entity.coach.CoachResponse
import com.kevin.netkick.domain.entity.country.CountryResponse
import com.kevin.netkick.domain.entity.fixtures.FixturesResponse
import com.kevin.netkick.domain.entity.league.LeagueResponse
import com.kevin.netkick.domain.entity.news.NewsResponse
import com.kevin.netkick.domain.entity.player.PlayerResponse
import com.kevin.netkick.domain.entity.player.ResponseP
import com.kevin.netkick.domain.entity.rounds.RoundsResponse
import com.kevin.netkick.domain.entity.standings.StandingsResponse
import com.kevin.netkick.domain.entity.teams.TeamResponse
import com.kevin.netkick.domain.entity.trophies.TrophiesResponse
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

    override suspend fun getCoachSearch(search: String): Flow<CoachResponse> {
        return repository.getCoachSearch(search)
    }

    override suspend fun getCoachTrophies(coach: Int): Flow<TrophiesResponse> {
        return repository.getCoachTrophies(coach)
    }

    override suspend fun getTeamSearch(search: String): Flow<TeamResponse> {
        return repository.getTeamSearch(search)
    }

    override suspend fun getPlayerSearch(search: String, team: Int): Flow<PlayerResponse> {
        return repository.getPlayerSearch(search,team)
    }

    override suspend fun getPlayerTrophies(player: Int): Flow<TrophiesResponse> {
       return repository.getPlayerTrophies(player)
    }

    override suspend fun getLeagueRounds(league: Int, season: Int): Flow<RoundsResponse> {
        return repository.getLeagueRounds(league,season)
    }

    override suspend fun getRoundMatches(
        league: Int,
        season: Int,
        round: String
    ): Flow<FixturesResponse> {
        return repository.getRoundMatches(league,season,round)
    }


}