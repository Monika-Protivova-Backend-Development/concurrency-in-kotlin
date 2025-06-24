package com.motycka.edu.threads

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.RuntimeException
import kotlin.concurrent.thread

private val logger = KotlinLogging.logger {}

fun main() {
    thread(name = "Moni") {
        try {
            logger.info { "${Thread.currentThread().name} is making your coffee." }
            Thread.sleep(5000)
            logger.info { "Your coffee is ready. Enjoy!" }
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
            logger.warn { "${Thread.currentThread().name} was interrupted." }
            throw RuntimeException(e)
        }
    }
}
