package com.kevin.netkick.presentation.di

import com.kevin.netkick.domain.DomainUseCase
import com.kevin.netkick.domain.DomainUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
abstract class UseCaseModule {
    @Binds
    abstract fun initUseCase(useCaseImpl: DomainUseCaseImpl): DomainUseCase
}