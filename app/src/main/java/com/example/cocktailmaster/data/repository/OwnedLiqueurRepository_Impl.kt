package com.example.cocktailmaster.data.repository

import android.content.Context
import com.example.cocktailmaster.data.CocktailApi
import com.example.cocktailmaster.data.db.AppDatabase
import com.example.cocktailmaster.data.interfaces.OwnedLiqueurRepository
import com.example.cocktailmaster.data.model.CocktailIngredient_Data
import kotlinx.coroutines.flow.Flow

class OwnedLiqueurRepository_Impl(
    val context: Context
): OwnedLiqueurRepository {
    override suspend fun provideAllIngredient(): Flow<List<CocktailIngredient_Data>> {
        return AppDatabase.getDatabase(context).ownedIngredientDao().getAll()
    }
}