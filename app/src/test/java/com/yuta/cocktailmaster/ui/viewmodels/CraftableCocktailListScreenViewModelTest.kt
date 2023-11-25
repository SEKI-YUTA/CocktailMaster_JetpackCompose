package com.yuta.cocktailmaster.ui.viewmodels

import com.yuta.cocktailmaster.data.DemoData
import com.yuta.cocktailmaster.data.interfaces.CocktailApiRepository
import com.yuta.cocktailmaster.ui.model.CocktailIngredient_UI
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.Before
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
    fun `カクテルを取得できる`() = runTest {
        val apiRepository: CocktailApiRepository = mockk {
            coEvery { craftableCocktails(DemoData.ingredientList.map { it.longName }) } returns DemoData.cocktailList.map { it.toUIModel() }
            coEvery { getAllCocktails() } returns DemoData.cocktailList.map { it.toUIModel() }
        }
        val viewModel = CraftableCocktailListScreenViewModelFactory(
            apiRepository = apiRepository,
            ingredientList = DemoData.ingredientList.map { it.toUIModel() }
        )

        viewModel.onEvent(CraftableCocktailListScreenEvent.FetchCocktailData)
        val viewState = viewModel.viewState.value

        viewState.craftableCocktailList shouldBe DemoData.cocktailList.map { it.toUIModel() }
        viewState.allCocktailList shouldBe DemoData.cocktailList.map { it.toUIModel() }
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