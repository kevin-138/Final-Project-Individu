package com.kevin.netkick

import android.app.Application
import com.kevin.netkick.network.di.DaggerDataComponent
import com.kevin.netkick.network.di.DataComponent
import com.kevin.netkick.presentation.di.AppComponent
import com.kevin.netkick.presentation.di.DaggerAppComponent

open class NetkickApplication:Application() {
    private val dataComponent: DataComponent by lazy{
        DaggerDataComponent.factory().create(applicationContext)
    }
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(dataComponent)
    }
}