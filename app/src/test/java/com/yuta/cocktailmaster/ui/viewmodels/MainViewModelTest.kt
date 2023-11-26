package com.yuta.cocktailmaster.ui.viewmodels

import com.yuta.cocktailmaster.data.interfaces.OwnedIngredientRepository
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import org.junit.Test

class MainViewModelTest {
    fun MainViewModelFactory(
        ownedIngredientRepository: OwnedIngredientRepository = mockk()
    ): MainViewModel {
        return MainViewModel(
            ownedIngredientRepository = ownedIngredientRepository
        )
    }

    @Test
    fun `ネットに繋がっているときはダイアログが表示されない`() {
        val viewModel = MainViewModelFactory()

        viewModel.setIsNetworkConnected(true)

        val viewState = viewModel.viewState.value
        viewState.isNetworkConnected shouldBe true
    }

    @Test
    fun `ネットに繋がっているときはダイアログが表示される`() {
        val viewModel = MainViewModelFactory()

        viewModel.setIsNetworkConnected(false)

        val viewState = viewModel.viewState.value
        viewState.isNetworkConnected shouldBe false
    }
}
