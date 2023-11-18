package com.yuta.cocktailmaster

import android.app.Application
import com.yuta.cocktailmaster.data.AppContainer

class CocktailMasterApplication: Application() {
    lateinit var appContainer: AppContainer
    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}