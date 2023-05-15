package com.kevin.netkick.presentation.view.viewmodels

import androidx.lifecycle.ViewModel
import com.kevin.netkick.domain.DomainUseCase
import com.kevin.netkick.domain.entity.general.Paging
import com.kevin.netkick.domain.entity.player.PlayerResponse
import com.kevin.netkick.domain.entity.teams.TeamResponse
import com.kevin.netkick.network.NetworkUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class PopularTeamViewModel @Inject constructor(private val useCase: DomainUseCase): ViewModel()  {

    private val _popularTeamsFlow = MutableStateFlow(TeamResponse(Paging(0,0),0, listOf()))
    val popularTeamsFlow : StateFlow<TeamResponse> =  _popularTeamsFlow

    suspend fun getPopularTeams(){
        useCase.getPopularTeamsHome(league = NetworkUtils.POPULAR_LEAGUE, season = NetworkUtils.POPULAR_SEASON).collectLatest {
            _popularTeamsFlow.value = it
        }
    }

    private val _detailTeamsFlow = MutableStateFlow(TeamResponse(Paging(0,0),0, listOf()))
    val detailTeamsFlow : StateFlow<TeamResponse> =  _detailTeamsFlow

    suspend fun getPopularTeamDetail(id: Int){
        useCase.getTeamDetail(id).collectLatest {
            _detailTeamsFlow.value = it
        }
    }

    private val _playerTeamsFlow = MutableStateFlow(PlayerResponse(Paging(0,0),0, listOf()))
    val playerTeamsFlow : StateFlow<PlayerResponse> =  _playerTeamsFlow

    suspend fun getTeamPlayer(scope:CoroutineScope,team: Int, season: Int){
        useCase.getPlayerList(scope:CoroutineScope,team,season).collectLatest {
            _playerTeamsFlow.value = it
        }
    }

}