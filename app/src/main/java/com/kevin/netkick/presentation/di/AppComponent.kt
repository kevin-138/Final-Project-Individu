package com.kevin.netkick.presentation.di

import com.kevin.netkick.network.di.DataComponent
import com.kevin.netkick.presentation.view.Main.activity.MainActivity
import dagger.Component

@AppScope
@Component(
  dependencies = [DataComponent::class],
  modules = [UseCaseModule::class]
)
interface AppComponent {

  @Component.Factory
  interface Factory {
    fun create(dataComponent: DataComponent): AppComponent
  }

  fun injectInto(mainActivity: MainActivity)
}