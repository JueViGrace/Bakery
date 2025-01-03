package com.bakery.core.types.common

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object Constants {
    const val APP_VERSION: String = "0.0.1"
    val currentTime: String = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()
    const val MINIMUM_LENGTH: Int = 4

    const val SNACK_BAR_MESSAGE_KEY: String = "SNACK_BAR_MESSAGE_KEY"
    const val SEARCH_BAR_TEXT_KEY: String = "SEARCH_BAR_TEXT_KEY"
}
