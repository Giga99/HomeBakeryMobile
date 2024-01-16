package com.pki.homebakery.features.details.presentation

import com.pki.homebakery.common.cakes
import com.pki.homebakery.features.dashboard.domain.Cake
import com.pki.homebakery.features.details.presentation.CakeDetailsViewModel.State
import com.pki.homebakery.ui.viewmodel.BaseViewModel
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class CakeDetailsViewModel(
    cakeId: String
) : BaseViewModel<State, Unit>(State(cakes.first { it.id == cakeId })) {

    data class State(
        val cake: Cake
    )
}
