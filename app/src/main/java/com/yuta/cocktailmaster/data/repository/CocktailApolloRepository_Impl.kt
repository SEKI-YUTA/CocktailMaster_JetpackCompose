package com.yuta.cocktailmaster.data.repository

import com.yuta.cocktailmaster.AllIngredientListQuery
import com.yuta.cocktailmaster.CraftableCocktailQuery
import com.yuta.cocktailmaster.data.api.CocktailApollo
import com.yuta.cocktailmaster.data.gql.extension.toCocktailUIModel
import com.yuta.cocktailmaster.data.gql.extension.toIngredientUIModel
import com.yuta.cocktailmaster.data.interfaces.CocktailApiRepository
import com.yuta.cocktailmaster.type.OwnedIngredientInput
import com.yuta.cocktailmaster.ui.model.CocktailIngredient_UI
import com.yuta.cocktailmaster.ui.model.Cocktail_UI

class CocktailApolloRepository_Impl : CocktailApiRepository {
    override suspend fun getAllIngredients(): List<CocktailIngredient_UI> {
        val response = CocktailApollo.apolloClient.query(AllIngredientListQuery()).execute()
        if (response.data == null) {
            return emptyList()
        }
        return response.data!!.ingredients.map { checkNotNull(it).toIngredientUIModel() }
    }

    override suspend fun craftableCocktails(query: List<String>): List<Cocktail_UI> {
        val response = CocktailApollo.apolloClient.query(
            CraftableCocktailQuery(
                OwnedIngredientInput(ingredients = query)
            )
        ).execute()
        if(response.data == null) return emptyList()
        return response.data!!.craftableCocktails.map { checkNotNull(it).toCocktailUIModel() }
    }
}
