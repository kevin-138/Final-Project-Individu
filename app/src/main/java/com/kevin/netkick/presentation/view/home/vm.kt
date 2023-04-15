package com.kevin.netkick.presentation.view.home

import androidx.lifecycle.ViewModel
import com.kevin.netkick.domain.DomainUseCase
import javax.inject.Inject

class vm @Inject constructor(private val useCase: DomainUseCase): ViewModel() {
}