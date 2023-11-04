package com.example.cocktailmaster.data.repository

import androidx.compose.runtime.snapshotFlow
import com.example.cocktailmaster.data.DemoData
import com.example.cocktailmaster.data.interfaces.OwnedIngredientRepository
import com.example.cocktailmaster.data.model.CocktailIngredient_Data
import kotlinx.coroutines.flow.Flow


class OwnedIngredientRepository_FakeImpl: OwnedIngredientRepository {
    override suspend fun provideAllIngredientFlow(): Flow<List<CocktailIngredient_Data>> {
        // 本番はここでデータベースからデータを引っ張ってくる処理をする
        return snapshotFlow {
            DemoData.ingredientList
        }
    }

    override suspend fun getAllIngredient(): List<CocktailIngredient_Data> {
        // 本番はここでデータベースからデータを引っ張ってくる処理をする
        return DemoData.ingredientList
    }

    override suspend fun deleteIngredient(ingredient: CocktailIngredient_Data) {
        TODO("Not yet implemented")
    }
}