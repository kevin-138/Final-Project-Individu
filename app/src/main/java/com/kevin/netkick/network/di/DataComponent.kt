package com.kevin.netkick.network.di

import android.content.Context
import com.kevin.netkick.domain.DomainRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RepositoryModule::class]
)

interface DataComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): DataComponent
    }

    fun provideRepository(): DomainRepository
}