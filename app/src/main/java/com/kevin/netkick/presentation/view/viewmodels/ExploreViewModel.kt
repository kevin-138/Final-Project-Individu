package com.kevin.netkick.presentation.view.viewmodels

import androidx.lifecycle.*
import com.kevin.netkick.domain.DomainUseCase
import com.kevin.netkick.domain.entity.fixtures.FixturesResponse
import com.kevin.netkick.domain.entity.general.Paging
import com.kevin.netkick.domain.entity.league.LeagueResponse
import com.kevin.netkick.domain.entity.rounds.RoundsResponse
import com.kevin.netkick.domain.entity.standings.StandingsResponse
import com.kevin.netkick.domain.entity.statistics.StatisticResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class ExploreViewModel @Inject constructor(private val useCase: DomainUseCase) : ViewModel() {

    private val _leagueByCountryFlow = MutableStateFlow(LeagueResponse(Paging(0, 0), 0, listOf(),error = ""))
    val leagueByCountryFlow: StateFlow<LeagueResponse> = _leagueByCountryFlow

    suspend fun getLeagueFilterCountry(country: String) {
        useCase.getLeagueFilterCountry(country).collectLatest {
            _leagueByCountryFlow.value = it
        }
    }

    private val leagueSearchQuery = MutableLiveData<String>()

    fun setSearchQuery(query: String) {
        leagueSearchQuery.value = query
    }

    val searchResults: LiveData<LeagueResponse> =
        leagueSearchQuery.switchMap { getSearchResults(it) }

    private fun getSearchResults(queryString: String) = liveData(Dispatchers.IO) {
        useCase.getLeagueSearch(queryString).collectLatest {
            emit(
                it
            )
        }
    }

    private val leagueStandingsSeason = MutableLiveData<Pair<Int, Int>>()

    fun setSearchQuery(query: Pair<Int, Int>) {
        leagueStandingsSeason.value = query
    }

    val standingResults: LiveData<StandingsResponse> =
        leagueStandingsSeason.switchMap { getLeagueStandings(it.first, it.second) }

    private fun getLeagueStandings(league: Int, season: Int) = liveData(Dispatchers.IO) {
        useCase.getLeagueStandings(league, season).collectLatest {
            emit(
                it
            )
        }
    }

    private val _leagueRounds = MutableStateFlow(RoundsResponse(Paging(0, 0), 0, listOf(),error = ""))
    val leagueRounds: StateFlow<RoundsResponse> = _leagueRounds

    suspend fun getLeagueRoundsBySeason(league: Int, season: Int) {
        useCase.getLeagueRounds(league, season).collectLatest {
            _leagueRounds.value = it
        }
    }


    private val leagueFixturesQuery = MutableLiveData<Triple<Int, Int, String>>()

    fun setFixtureQuery(query: Triple<Int, Int, String>) {
        leagueFixturesQuery.value = query
    }

    val fixtureResult: LiveData<FixturesResponse> =
        leagueFixturesQuery.switchMap { getLeagueStandings(it.first, it.second, it.third) }

    private fun getLeagueStandings(league: Int, season: Int, round: String) =
        liveData(Dispatchers.IO) {
            useCase.getRoundMatches(league, season, round).collectLatest {
                emit(
                    it
                )
            }
        }

    private val _fixtureStatistics = MutableStateFlow(StatisticResponse(Paging(0, 0), 0, listOf(),error = ""))
    val fixtureStatistics: StateFlow<StatisticResponse> = _fixtureStatistics

    suspend fun getFixtureStatistic(fixture: Int) {
        useCase.getMatchesStatistic(fixture).collectLatest {
            _fixtureStatistics.value = it
        }
    }

}