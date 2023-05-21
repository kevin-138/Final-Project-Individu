package com.kevin.netkick.presentation.view.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kevin.netkick.domain.DomainUseCase
import com.kevin.netkick.presentation.view.viewmodels.ExploreViewModel
import com.kevin.netkick.presentation.view.viewmodels.MainViewModel
import com.kevin.netkick.presentation.view.viewmodels.PopularTeamViewModel
import com.kevin.netkick.presentation.view.viewmodels.TrophiesViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val useCaseImpl: DomainUseCase) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(useCaseImpl) as T
            modelClass.isAssignableFrom(PopularTeamViewModel::class.java) -> PopularTeamViewModel(useCaseImpl) as T
            modelClass.isAssignableFrom(ExploreViewModel::class.java) -> ExploreViewModel(useCaseImpl) as T
            modelClass.isAssignableFrom(TrophiesViewModel::class.java) -> TrophiesViewModel(useCaseImpl) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}