package com.pki.homebakery.features.dashboard.data

import com.pki.homebakery.common.cakes
import com.pki.homebakery.features.dashboard.domain.Cake
import org.koin.core.annotation.Factory

@Factory
class DashboardService {

    suspend fun getAllCakes(): List<Cake> = cakes
}
