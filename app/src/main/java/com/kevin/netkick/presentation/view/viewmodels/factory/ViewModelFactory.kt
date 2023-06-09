package com.kevin.netkick.presentation.view.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kevin.netkick.domain.DomainUseCase
import com.kevin.netkick.presentation.view.viewmodels.MainViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val useCaseImpl: DomainUseCase) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(useCaseImpl) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}