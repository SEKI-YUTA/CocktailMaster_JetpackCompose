package com.example.cocktailmaster.data

import com.example.cocktailmaster.data.model.CocktailIngredient_Data
import com.example.cocktailmaster.data.model.Cocktail_Data

class DemoData {
    companion object {
        val cocktailList = listOf<Cocktail_Data>(
            Cocktail_Data("ジンバッグ", null, "ジンとジンジャーエールを混ぜたカクテル", true, listOf(
//                CocktailIngredient_Data(1, "ジン", null, true, "ジン"),
//                CocktailIngredient_Data(2, "ジンジャーエール", null, true, "ジンジャーエール")
            "ジン", "ジンジャーエール"
            )),
        )

        val liqueurList = listOf(
            CocktailIngredient_Data(1, "Vodka", null, true, "A clear distilled alcoholic beverage with a neutral flavor."),
            CocktailIngredient_Data(2, "Gin", null, true, "A spirit made from juniper berries and various botanicals."),
            CocktailIngredient_Data(3, "Rum", null, true, "A sugarcane-based spirit, often aged in oak barrels."),
            CocktailIngredient_Data(4, "Tequila", null, true, "A distilled beverage made from the blue agave plant."),
            CocktailIngredient_Data(5, "Triple Sec", null, true, "An orange-flavored liqueur."),
            CocktailIngredient_Data(6, "Whiskey", null, true, "A distilled alcoholic beverage made from fermented grain mash."),
            CocktailIngredient_Data(7, "Amaretto", null, true, "A sweet Italian liqueur with almond flavor."),
            CocktailIngredient_Data(8, "Baileys", null, true, "An Irish cream liqueur with a blend of whiskey and cream."),
            CocktailIngredient_Data(9, "Cointreau", null, true, "A brand of triple sec, clear and colorless orange-flavored liqueur."),
            CocktailIngredient_Data(10, "Kahlua", null, true, "A coffee-flavored liqueur from Mexico.")
        )
    }
}