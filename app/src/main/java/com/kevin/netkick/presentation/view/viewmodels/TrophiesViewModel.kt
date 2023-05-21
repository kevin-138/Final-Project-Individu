package com.kevin.netkick.presentation.view.viewmodels

import androidx.lifecycle.*
import com.kevin.netkick.domain.DomainUseCase
import com.kevin.netkick.domain.entity.league.LeagueResponse
import com.kevin.netkick.domain.entity.player.PlayerResponse
import kotlinx.coroutines.Dispatchers
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
}