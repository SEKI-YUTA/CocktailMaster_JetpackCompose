package com.yuta.cocktailmaster.ui.model

data class Cocktail_UI(
    val cocktailId: Int = 0,
    val name: String,
    val description: String = "",
    val vol: Int = 0,
    val ingredientCount: Int = 0,
    val category: String = "",
    val cocktailCategoryId: Int = 0,
    val ingredients: List<CocktailIngredient_UI>
)
