package com.pki.homebakery.ui

import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun Long.secondsToPrettyString(): String {
    val duration = this.toDuration(DurationUnit.SECONDS)
    return when {
        duration.inWholeMinutes < 1 -> {
            "Just Now"
        }

        duration.inWholeHours < 1 -> {
            "${duration.inWholeMinutes}m"
        }

        duration.inWholeDays < 1 -> {
            "${duration.inWholeHours}h"
        }

        else -> {
            "${duration.inWholeDays}d"
        }
    }
}
