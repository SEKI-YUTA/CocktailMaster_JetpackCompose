package com.example.cocktailmaster

import android.app.Application
import com.example.cocktailmaster.data.AppContainer
import com.example.cocktailmaster.data.DefaultContainer

class CocktailMasterApplication: Application() {
    lateinit var appContainer: DefaultContainer
    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}