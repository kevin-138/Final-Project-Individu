package com.kevin.netkick.network.di

import com.kevin.netkick.domain.DomainRepository
import com.kevin.netkick.network.NetworkDataRepositoryImpl
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(repositoryImpl: NetworkDataRepositoryImpl): DomainRepository


}