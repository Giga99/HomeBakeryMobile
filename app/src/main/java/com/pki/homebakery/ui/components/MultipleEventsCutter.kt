package com.pki.homebakery.ui.components

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role

class MultipleEventsCutter {
    companion object {
        private const val CLICK_THROTTLE_MS = 300L
        private var lastEventTimeMs: Long = 0

        fun processEvent(event: () -> Unit) {
            val now = System.currentTimeMillis()
            if (now - lastEventTimeMs > CLICK_THROTTLE_MS) {
                lastEventTimeMs = now
                event()
            }
        }
    }
}

fun onClickThrottled(onClick: () -> Unit): () -> Unit = { MultipleEventsCutter.processEvent(onClick) }

fun (() -> Unit).throttled(): () -> Unit = onClickThrottled(this)

fun Modifier.clickableThrottled(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit,
) = this.clickable(
    enabled = enabled,
    onClickLabel = onClickLabel,
    role = role,
    onClick = onClick.throttled(),
)

fun Modifier.clickableThrottled(
    interactionSource: MutableInteractionSource,
    indication: Indication?,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit,
) = this.clickable(
    enabled = enabled,
    onClickLabel = onClickLabel,
    role = role,
    indication = indication,
    interactionSource = interactionSource,
    onClick = onClick.throttled(),
)
