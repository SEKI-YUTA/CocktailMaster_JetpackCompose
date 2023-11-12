package com.yuta.cocktailmaster.data.model

import com.yuta.cocktailmaster.ui.model.Cocktail_UI
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// データソースから引っ張ってくる時のデータ型そのためUIに表示させる時はtoUIModel()を呼び出してUI用のデータ型に変換する
@Serializable
data class Cocktail_Data(
    @SerialName("cocktail_id")
    val cocktailId: Int = 0,

    @SerialName("name")
    val name: String,

    @SerialName("description")
    val description: String = "",

    @SerialName("vol")
    val vol: Int = 0,

    @SerialName("parent_cocktail_id")
    val parentCocktailId: Int = 0,

    @SerialName("parent_name")
    val parentName: String = "",

    @SerialName("ingredient_count")
    val ingredientCount: Int = 0,

    @SerialName("category")
    val category: String = "",

    @SerialName("cocktail_category_id")
    val cocktailCategoryId: Int = 0,

    @SerialName("ingredients")
    val ingredients: List<CocktailIngredient_Data> = emptyList(),

    val fetchFailed: Boolean = false,
) {
    fun toUIModel(): Cocktail_UI {
        return Cocktail_UI(
            cocktailId = cocktailId,
            name = name,
            description = description,
            vol = vol,
            parentCocktailId = parentCocktailId,
            parentName = parentName,
            ingredientCount = ingredientCount,
            category = category,
            cocktailCategoryId = cocktailCategoryId,
            ingredients = ingredients.map { it.toUIModel() }
        )
    }
}