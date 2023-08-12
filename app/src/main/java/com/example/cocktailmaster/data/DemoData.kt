package com.example.cocktailmaster.data

import com.example.cocktailmaster.data.model.CocktailIngredient_Data
import com.example.cocktailmaster.data.model.Cocktail_Data

class DemoData {
    companion object {
        val cocktailList = listOf<Cocktail_Data>(
            Cocktail_Data("ジンバッグ", null, "ジンとジンジャーエールを混ぜたカクテル", true, listOf(
                CocktailIngredient_Data("ジン", null, true, "ジン"),
                CocktailIngredient_Data("ジンジャーエール", null, true, "ジンジャーエール")
            )),
        )

        val liqueurList = listOf(
            CocktailIngredient_Data("Vodka", null, true, "A clear distilled alcoholic beverage with a neutral flavor."),
            CocktailIngredient_Data("Gin", null, true, "A spirit made from juniper berries and various botanicals."),
            CocktailIngredient_Data("Rum", null, true, "A sugarcane-based spirit, often aged in oak barrels."),
            CocktailIngredient_Data("Tequila", null, true, "A distilled beverage made from the blue agave plant."),
            CocktailIngredient_Data("Triple Sec", null, true, "An orange-flavored liqueur."),
            CocktailIngredient_Data("Whiskey", null, true, "A distilled alcoholic beverage made from fermented grain mash."),
            CocktailIngredient_Data("Amaretto", null, true, "A sweet Italian liqueur with almond flavor."),
            CocktailIngredient_Data("Baileys", null, true, "An Irish cream liqueur with a blend of whiskey and cream."),
            CocktailIngredient_Data("Cointreau", null, true, "A brand of triple sec, clear and colorless orange-flavored liqueur."),
            CocktailIngredient_Data("Kahlua", null, true, "A coffee-flavored liqueur from Mexico.")
        )
    }
}