package com.kevin.netkick.presentation.view.viewmodels

import androidx.lifecycle.*
import com.kevin.netkick.domain.DomainUseCase
import com.kevin.netkick.domain.entity.coach.CoachResponse
import com.kevin.netkick.domain.entity.general.Paging
import com.kevin.netkick.domain.entity.league.LeagueResponse
import com.kevin.netkick.domain.entity.player.PlayerResponse
import com.kevin.netkick.domain.entity.trophies.TrophiesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class TrophiesViewModel @Inject constructor(private val useCase: DomainUseCase): ViewModel()  {

    private val leagueSearchQuery = MutableLiveData<String>()

    fun setSearchQuery(query:String){
        leagueSearchQuery.value = query
    }

    val searchResults: LiveData<LeagueResponse> = leagueSearchQuery.switchMap { getSearchResults(it) }

    private fun getSearchResults(queryString: String) = liveData(Dispatchers.IO) {
        useCase.getLeagueSearch(queryString).collectLatest {
            emit(
                it
            )
        }
    }

    private val leagueTopscorer = MutableLiveData<Pair<Int,Int>>()

    fun setTopScoreQuery(query:Pair<Int,Int>){
        leagueTopscorer.value = query
    }

    val topScoreResults: LiveData<PlayerResponse> = leagueTopscorer.switchMap {getLeagueTopscorer(it.first,it.second) }

    private fun getLeagueTopscorer(league: Int, season:Int) = liveData(Dispatchers.IO) {
        useCase.getLeagueTopscore(league,season).collectLatest {
            emit(
                it
            )
        }
    }

    private val coachSearch = MutableLiveData<String>()

    fun setCoachSearchQuery(query:String){
        coachSearch.value = query
    }

    val coachSearchResults: LiveData<CoachResponse> = coachSearch.switchMap {getCoachSearch(it)}

    private fun getCoachSearch(query: String) = liveData(Dispatchers.IO) {
        useCase.getCoachSearch(query).collectLatest {
            emit(
                it
            )
        }
    }

    private val _trophiesFlow = MutableStateFlow(TrophiesResponse(Paging(0,0),0, listOf()))
    val trophiesFlow : StateFlow<TrophiesResponse> =  _trophiesFlow

    suspend fun getTrophies(coach:Int){
        useCase.getCoachTrophies(coach).collectLatest {
            _trophiesFlow.value = it
        }
    }
}