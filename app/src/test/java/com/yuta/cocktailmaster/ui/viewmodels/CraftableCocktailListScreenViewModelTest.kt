package com.yuta.cocktailmaster.ui.viewmodels

import com.yuta.cocktailmaster.data.interfaces.CocktailApiRepository
import com.yuta.cocktailmaster.ui.model.CocktailIngredient_UI
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import org.junit.Test

class CraftableCocktailListScreenViewModelTest {
    fun CraftableCocktailListScreenViewModelFactory(
        apiRepository: CocktailApiRepository = mockk(),
        ingredientList: List<CocktailIngredient_UI> = mockk()
    ): CraftableCocktailListScreenViewModel {
        return CraftableCocktailListScreenViewModel(
            apiRepository = apiRepository,
            ingredientList = ingredientList
        )
    }

    @Test
    fun `作れるカクテルタブを選択できる`() {
        val viewModel = CraftableCocktailListScreenViewModelFactory()

        viewModel.setSelectedTab(TabItems.CRAFTABLE)
        val viewState = viewModel.viewState.value

        viewState.selectedTab shouldBe TabItems.CRAFTABLE
    }

    @Test
    fun `すべてのカクテルタブを選択できる`() {
        val viewModel = CraftableCocktailListScreenViewModelFactory()

        viewModel.setSelectedTab(TabItems.ALL_COCKTAILS)
        val viewState = viewModel.viewState.value

        viewState.selectedTab shouldBe TabItems.ALL_COCKTAILS
    }
}
