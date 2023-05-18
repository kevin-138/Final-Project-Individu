package com.kevin.netkick.presentation.view.viewmodels

import androidx.lifecycle.ViewModel
import com.kevin.netkick.domain.DomainUseCase
import javax.inject.Inject

class ExploreViewModel @Inject constructor(private val useCase: DomainUseCase): ViewModel()  {

}