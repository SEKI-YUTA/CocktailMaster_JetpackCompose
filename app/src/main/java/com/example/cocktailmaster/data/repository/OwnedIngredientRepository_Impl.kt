package com.example.cocktailmaster.data.repository

import android.content.Context
import com.example.cocktailmaster.data.db.AppDatabase
import com.example.cocktailmaster.data.interfaces.OwnedIngredientRepository
import com.example.cocktailmaster.data.model.CocktailIngredient_Data
import kotlinx.coroutines.flow.Flow

class OwnedIngredientRepository_Impl(
    val context: Context
): OwnedIngredientRepository {
    override suspend fun provideAllIngredientFlow(): Flow<List<CocktailIngredient_Data>> {
        return AppDatabase.getDatabase(context).ownedIngredientDao().getAllCocktailIngredientFlow()
    }

    override suspend fun getAllIngredient(): List<CocktailIngredient_Data> {
        return AppDatabase.getDatabase(context).ownedIngredientDao().getAllCocktailIngredient()
    }

    override suspend fun deleteIngredient(ingredient: CocktailIngredient_Data) {
        AppDatabase.getDatabase(context).ownedIngredientDao().deleteIngredient(ingredient)
    }
}