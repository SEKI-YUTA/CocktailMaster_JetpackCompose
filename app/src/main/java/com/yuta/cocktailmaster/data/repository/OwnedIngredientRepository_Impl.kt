package com.yuta.cocktailmaster.data.repository

import android.content.Context
import com.yuta.cocktailmaster.data.db.AppDatabase
import com.yuta.cocktailmaster.data.interfaces.OwnedIngredientRepository
import com.yuta.cocktailmaster.data.model.CocktailIngredient_Data
import kotlinx.coroutines.flow.Flow

class OwnedIngredientRepository_Impl(
    val context: Context
) : OwnedIngredientRepository {
    override suspend fun provideAllIngredientFlow(): Flow<List<CocktailIngredient_Data>> {
        return AppDatabase.getDatabase(context).ownedIngredientDao().getAllCocktailIngredientFlow()
    }

    override suspend fun getAllIngredient(): List<CocktailIngredient_Data> {
        return AppDatabase.getDatabase(context).ownedIngredientDao().getAllCocktailIngredient()
    }

    override suspend fun deleteIngredient(ingredient: CocktailIngredient_Data) {
        AppDatabase.getDatabase(context).ownedIngredientDao().deleteIngredient(ingredient)
    }

    override suspend fun addOwnedIngredient(ingredient: CocktailIngredient_Data) {
        AppDatabase.getDatabase(context).ownedIngredientDao().insertIngredient(ingredient)
    }

    override suspend fun editOwnedIngredient(ingredient: CocktailIngredient_Data) {
        AppDatabase.getDatabase(context).ownedIngredientDao().updateIngredient(ingredient)
    }
}
