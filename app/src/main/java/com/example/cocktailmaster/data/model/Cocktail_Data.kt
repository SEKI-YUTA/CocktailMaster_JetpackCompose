package com.example.cocktailmaster.data.model

import com.example.cocktailmaster.ui.model.Cocktail_UI
import kotlinx.serialization.Serializable

// データソースから引っ張ってくる時のデータ型そのためUIに表示させる時はtoUIModel()を呼び出してUI用のデータ型に変換する
@Serializable
data class Cocktail_Data(
    val name: String,
    val imageUri: String? = null,
    val description: String = "",
    val craftable: Boolean = false,
    val ingredients: List<String>
) {
    fun toUIModel(): Cocktail_UI {
        return Cocktail_UI(
            name = name,
            imageUri = imageUri,
            description = description,
            craftable = craftable,
            ingredients = ingredients
        )
    }
}
