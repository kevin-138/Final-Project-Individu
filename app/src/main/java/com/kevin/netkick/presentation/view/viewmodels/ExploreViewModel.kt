package com.kevin.netkick.presentation.view.viewmodels

import androidx.lifecycle.*
import com.kevin.netkick.domain.DomainUseCase
import com.kevin.netkick.domain.entity.general.Paging
import com.kevin.netkick.domain.entity.league.LeagueResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class ExploreViewModel @Inject constructor(private val useCase: DomainUseCase): ViewModel()  {

    private val _leagueByCountryFlow = MutableStateFlow(LeagueResponse(Paging(0,0),0, listOf()))
    val leagueByCountryFlow : StateFlow<LeagueResponse> =   _leagueByCountryFlow

    suspend fun getLeagueFilterCountry(country:String){
        useCase.getLeagueFilterCountry(country).collectLatest {
            _leagueByCountryFlow.value = it
        }
    }

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

}