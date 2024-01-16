package com.pki.homebakery.features.details.data

import com.pki.homebakery.common.cakes
import com.pki.homebakery.features.dashboard.domain.Cake
import org.koin.core.annotation.Factory

@Factory
class CakeDetailsService {

    suspend fun getCakeDetails(cakeId: String): Cake {
        return cakes.firstOrNull { it.id == cakeId } ?: error("Cake not found")
    }
}
