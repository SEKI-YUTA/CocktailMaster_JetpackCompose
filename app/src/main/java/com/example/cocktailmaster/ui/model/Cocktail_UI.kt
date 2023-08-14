package com.example.cocktailmaster.ui.model

data class Cocktail_UI(
    val name: String,
    val imageUri: String?,
    val description: String = "",
    val craftable: Boolean = false,
    val ingredients: List<String>
)
