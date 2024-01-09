package com.pki.homebakery.features.dashboard.domain

import androidx.annotation.DrawableRes
import java.time.Instant

data class Cake(
    val id: String,
    val title: String,
    val subtitle: String,
    @DrawableRes val icon: Int,
    val description: String,
    val ingredients: List<String>,
    val comments: List<Comment>
)

data class Comment(
    val text: String,
    val username: String,
    val date: Instant
)
