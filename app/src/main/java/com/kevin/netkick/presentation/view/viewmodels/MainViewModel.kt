package com.kevin.netkick.presentation.view.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevin.netkick.domain.DomainUseCase
import com.kevin.netkick.domain.entity.fixtures.FixturesResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    suspend fun getLiveMatches(live:String): Flow<FixturesResponse>{
        return useCase.getLiveMatches(live)
    }

}