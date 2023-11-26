package com.yuta.cocktailmaster.data.interfaces

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey

val IS_ONBOARDING_FINISHED_KEY = booleanPreferencesKey("is_onboarding_finished")

interface AppStatusRepository {
    suspend fun readIsOnboardingFinished(context: Context): Boolean
    suspend fun writeIsOnboardingFinished(context: Context, isFinished: Boolean)
}
