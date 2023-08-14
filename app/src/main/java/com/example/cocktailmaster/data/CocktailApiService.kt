package com.example.cocktailmaster.data

import com.example.cocktailmaster.data.model.CocktailIngredient_Data
import com.example.cocktailmaster.data.model.Cocktail_Data
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "http://10.0.2.2:9090/"

private val json = Json { ignoreUnknownKeys = true }

private val retrofit = Retrofit.Builder()
    .addConverterFactory(
        json.asConverterFactory(MediaType.get("application/json"))
    )
    .baseUrl(BASE_URL)
    .build()

interface CocktailApiService {
    @GET("ingredients")
    suspend fun getAllIngredients(): List<CocktailIngredient_Data>

    @GET("cocktails")
    suspend fun craftableCocktails(@Query("ingredients[]") querys: List<String>): List<Cocktail_Data>
}

object CocktailApi {
    val retrofitService: CocktailApiService by lazy {
        retrofit.create(CocktailApiService::class.java)
    }
}