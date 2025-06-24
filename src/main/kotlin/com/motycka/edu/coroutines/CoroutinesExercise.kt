package com.motycka.edu.coroutines

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import java.util.concurrent.atomic.AtomicInteger

private val logger = KotlinLogging.logger {}

fun main(): Unit = runBlocking {
    val orders = AtomicInteger(10)
    val coffeeMachine = Mutex()

    launch { makeCoffee(orders = orders, coffeeMachine = coffeeMachine) }
    launch { makeCoffee(orders = orders, coffeeMachine = coffeeMachine) }
}

private suspend fun makeCoffee(orders: AtomicInteger, coffeeMachine: Mutex) {
    while (orders.get() > 0) {
        delay(1000) // Non-blocking sleep
        if (coffeeMachine.tryLock()) {
            logger.info { "${Thread.currentThread().name} has the coffee machine." }
            try {
                if (orders.decrementAndGet() < 0) {
                    logger.info { "No more orders to process." }
                } else {
                    logger.info { "${Thread.currentThread().name} is making your coffee." }
                    logger.info { "Your coffee is ready. Enjoy!" }
                }
            } catch (e: Exception) {
                logger.warn { "${Thread.currentThread().name} was interrupted." }
                throw RuntimeException(e)
            } finally {
                coffeeMachine.unlock()
                logger.info { "${Thread.currentThread().name} released the coffee machine." }
            }
        } else {
            logger.warn { "Coffee machine is busy. Please wait." }
            delay(1000) // Non-blocking sleep
        }
    }
}
