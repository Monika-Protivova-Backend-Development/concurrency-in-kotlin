@file:OptIn(DelicateCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package com.motycka.edu.coffeeshop

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

private val logger = KotlinLogging.logger {}

class CoffeeShop(

) {

    init {
        logger.info { "We are now open" }
    }

    companion object {
        const val TIME_TO_RECHARGE = 200
    }
}

