package com.example.cocktailmaster.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.cocktailmaster.data.model.CocktailIngredient_Data
import kotlinx.coroutines.flow.Flow

@Dao
interface OwnedIngredientDAO {
    @Query("SELECT * FROM owned_ingredient")
    fun getAll(): Flow<List<CocktailIngredient_Data>>

    @Insert
    fun insertIngredient(ingredient: CocktailIngredient_Data)
}