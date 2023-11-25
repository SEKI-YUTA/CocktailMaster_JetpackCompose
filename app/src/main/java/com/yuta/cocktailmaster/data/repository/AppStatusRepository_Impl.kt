package com.yuta.cocktailmaster.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.yuta.cocktailmaster.data.interfaces.AppStatusRepository
import com.yuta.cocktailmaster.data.interfaces.IS_ONBOARDING_FINISHED_KEY
import com.yuta.cocktailmaster.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AppStatusRepository_Impl : AppStatusRepository {
    override suspend fun readIsOnboardingFinished(context: Context): Boolean {
        val flg = context.dataStore.data.map { preference ->
            preference[IS_ONBOARDING_FINISHED_KEY] ?: false
        }.first()
        return flg
    }

    override suspend fun writeIsOnboardingFinished(context: Context, isFinished: Boolean) {
        context.dataStore.edit {
            it[IS_ONBOARDING_FINISHED_KEY] = isFinished
        }
    }
}