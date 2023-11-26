package com.yuta.cocktailmaster.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yuta.cocktailmaster.data.db.dao.OwnedIngredientDAO
import com.yuta.cocktailmaster.data.model.CocktailIngredient_Data

@Database(entities = arrayOf(CocktailIngredient_Data::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ownedIngredientDao(): OwnedIngredientDAO
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}
