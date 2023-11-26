package com.yuta.cocktailmaster.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.yuta.cocktailmaster.data.model.CocktailIngredient_Data
import kotlinx.coroutines.flow.Flow

@Dao
interface OwnedIngredientDAO {
    @Query("SELECT * FROM owned_ingredient")
    fun getAllCocktailIngredientFlow(): Flow<List<CocktailIngredient_Data>>

    @Query("SELECT * FROM owned_ingredient")
    fun getAllCocktailIngredient(): List<CocktailIngredient_Data>

    @Insert
    fun insertIngredient(ingredient: CocktailIngredient_Data)

    @Update
    fun updateIngredient(ingredient: CocktailIngredient_Data)

    @Delete
    fun deleteIngredient(ingredient: CocktailIngredient_Data)
}
