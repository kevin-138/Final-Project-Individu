package com.kevin.netkick.presentation.view.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevin.netkick.domain.DomainUseCase
import com.kevin.netkick.domain.entity.fixtures.FixturesResponse
import com.kevin.netkick.domain.entity.general.Paging
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
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

}