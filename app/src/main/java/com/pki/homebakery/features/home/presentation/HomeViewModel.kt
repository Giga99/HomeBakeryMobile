package com.pki.homebakery.features.home.presentation

import com.pki.homebakery.features.home.presentation.HomeViewModel.State
import com.pki.homebakery.ui.viewmodel.BaseViewModel
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel : BaseViewModel<State, Unit>(State()) {

    fun changeSelectedTab(newSelectedTab: BottomNavigationItem) =
        updateState { state.copy(selectedTab = newSelectedTab) }

    data class State(
        val selectedTab: BottomNavigationItem = BottomNavigationItem.Dashboard,
    )

    enum class BottomNavigationItem {
        Dashboard, Notification, Cart, Profile
    }
}
