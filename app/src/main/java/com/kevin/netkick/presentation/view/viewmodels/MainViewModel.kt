package com.kevin.netkick.presentation.view.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevin.netkick.domain.DomainUseCase
import com.kevin.netkick.domain.entity.fixtures.FixturesResponse
import com.kevin.netkick.domain.entity.general.Paging
import com.kevin.netkick.domain.entity.teams.TeamResponse
import com.kevin.netkick.network.NetworkUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class MainViewModel @Inject constructor(private val useCase: DomainUseCase):ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch{
            delay(2.seconds)
            _isLoading.value = false
        }
    }

    private val _liveScoreFlow = MutableStateFlow(FixturesResponse(Paging(0,0),0, listOf()))
    val liveScoreFlow : StateFlow<FixturesResponse> =  _liveScoreFlow

    suspend fun getLiveMatches(live:String){
        useCase.getLiveMatches(live).collectLatest {
            _liveScoreFlow.value = it
        }
    }

    private val _popularTeamsFlow = MutableStateFlow(TeamResponse(Paging(0,0),0, listOf()))
    val popularTeamsFlow : StateFlow<TeamResponse> =  _popularTeamsFlow

    suspend fun getPopularTeams(){
        useCase.getPopularTeamsHome(league = NetworkUtils.POPULAR_LEAGUE, season = NetworkUtils.POPULAR_SEASON).collectLatest {
            _popularTeamsFlow.value = it
        }
    }

}