package com.pki.homebakery.features.addcomment.data

import com.pki.homebakery.common.PrefsService
import com.pki.homebakery.common.cakes
import com.pki.homebakery.features.dashboard.domain.Comment
import org.koin.core.annotation.Factory
import java.time.Instant

@Factory
class CommentsService(
    private val prefsService: PrefsService
) {

    suspend fun addComment(cakeId: String, comment: String) {
        val cake = cakes.find { it.id == cakeId } ?: error("Cake not found")
        val currentUsername = prefsService.getCurrentUser() ?: error("User not found")
        val newComment = Comment(comment, currentUsername, Instant.now())
        val updatedCake = cake.copy(comments = cake.comments + newComment)
        cakes = cakes.map { if (it.id == cakeId) updatedCake else it }
    }
}
