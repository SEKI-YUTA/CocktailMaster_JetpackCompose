package com.yuta.cocktailmaster.data.gql.extension

import com.yuta.cocktailmaster.AllCocktailListQuery
import com.yuta.cocktailmaster.AllIngredientListQuery
import com.yuta.cocktailmaster.CraftableCocktailQuery
import com.yuta.cocktailmaster.ui.model.CocktailIngredient_UI
import com.yuta.cocktailmaster.ui.model.Cocktail_UI

fun CraftableCocktailQuery.CraftableCocktail.toCocktailUIModel(): Cocktail_UI {
    return Cocktail_UI(
        cocktailId = checkNotNull(this.cocktail_id).toInt(),
        name = checkNotNull(this.name),
        description = checkNotNull(this.description),
        vol = checkNotNull(this.vol),
        ingredientCount = checkNotNull(this.ingredient_count),
        category = checkNotNull(this.category),
        cocktailCategoryId = checkNotNull(this.cocktail_category_id),
        ingredients = this.ingredients.map { checkNotNull(it).toIngredientUIModel() }
    )
}

fun CraftableCocktailQuery.Ingredient.toIngredientUIModel(): CocktailIngredient_UI {
    return CocktailIngredient_UI(
        id = checkNotNull(this.ingredient_id).toInt(),
        shortName = checkNotNull(this.shortname),
        longName = checkNotNull(this.longname),
        description = checkNotNull(this.description),
        vol = checkNotNull(this.vol),
        ingredientCategoryId = checkNotNull(this.ingredient_category_id).toInt(),
        category = checkNotNull(this.category)
    )
}

fun AllCocktailListQuery.Cocktail.toCocktailUIModel(): Cocktail_UI {
    return Cocktail_UI(
        cocktailId = checkNotNull(this.cocktail_id).toInt(),
        name = checkNotNull(this.name),
        description = checkNotNull(this.description),
        vol = checkNotNull(this.vol),
        ingredientCount = checkNotNull(this.ingredient_count),
        category = checkNotNull(this.category),
        cocktailCategoryId = checkNotNull(this.cocktail_category_id),
        ingredients = this.ingredients.map { checkNotNull(it).toIngredientUIModel() }
    )
}

fun AllCocktailListQuery.Ingredient.toIngredientUIModel(): CocktailIngredient_UI {
    return CocktailIngredient_UI(
        id = checkNotNull(this.ingredient_id).toInt(),
        shortName = checkNotNull(this.shortname),
        longName = checkNotNull(this.longname),
        description = checkNotNull(this.description),
        vol = checkNotNull(this.vol),
        ingredientCategoryId = checkNotNull(this.ingredient_category_id).toInt(),
        category = checkNotNull(this.category)
    )
}

fun AllIngredientListQuery.Ingredient.toIngredientUIModel(): CocktailIngredient_UI {
    return CocktailIngredient_UI(
        id = checkNotNull(this.ingredient_id).toInt(),
        shortName = checkNotNull(this.shortname),
        longName = checkNotNull(this.longname),
        description = checkNotNull(this.description),
        vol = checkNotNull(this.vol),
        ingredientCategoryId = checkNotNull(this.ingredient_category_id).toInt(),
        category = checkNotNull(this.category)
    )
}
