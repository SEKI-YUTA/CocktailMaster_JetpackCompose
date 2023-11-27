package com.yuta.cocktailmaster.data.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.yuta.cocktailmaster.BuildConfig
import com.yuta.cocktailmaster.data.model.CocktailIngredient_Data
import com.yuta.cocktailmaster.data.model.Cocktail_Data
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = BuildConfig.BASE_URL

private val json = Json { ignoreUnknownKeys = true }

private val retrofit = Retrofit.Builder()
    .addConverterFactory(
        json.asConverterFactory("application/json".toMediaType())
    )
    .baseUrl(BASE_URL)
    .build()

interface CocktailApiService {
    @GET("ingredients")
    suspend fun getAllIngredients(): List<CocktailIngredient_Data>

    @GET("compute/cocktails")
    suspend fun craftableCocktails(
        @Query("ingredients[]") querys: List<String>
    ): List<Cocktail_Data>

    @GET("cocktails/all")
    suspend fun getAllCocktail(): List<Cocktail_Data>
}

object CocktailApi {
    val retrofitService: CocktailApiService by lazy {
        retrofit.create(CocktailApiService::class.java)
    }
}
