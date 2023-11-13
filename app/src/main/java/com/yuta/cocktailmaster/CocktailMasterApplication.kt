package com.yuta.cocktailmaster

import android.app.Application
import com.yuta.cocktailmaster.data.AppContainer
import com.yuta.cocktailmaster.data.DefaultContainer

class CocktailMasterApplication: Application() {
    lateinit var appContainer: DefaultContainer
    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}