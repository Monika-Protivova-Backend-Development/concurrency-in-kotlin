package com.motycka.edu.threads

import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.Semaphore
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

private val logger = KotlinLogging.logger {}

fun main() {
    val orders = AtomicInteger(10)
    val coffeeMachine = Semaphore(1)

    thread(name = "Moni") { makeCoffee(orders = orders, coffeeMachine = coffeeMachine) }
    thread(name = "Irene") { makeCoffee(orders = orders, coffeeMachine = coffeeMachine) }
}

private fun makeCoffee(orders: AtomicInteger, coffeeMachine: Semaphore) {
    while (orders.get() > 0) {
        Thread.sleep(1000)
        if (coffeeMachine.tryAcquire()) {
            logger.info { "${Thread.currentThread().name} has the coffee machine." }
            try {
                if (orders.decrementAndGet() < 0) {
                    logger.info { "No more orders to process." }
                } else {
                    logger.info { "${Thread.currentThread().name} is making your coffee." }
                    logger.info { "Your coffee is ready. Enjoy!" }
                }
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
                logger.warn { "${Thread.currentThread().name} was interrupted." }
                throw RuntimeException(e)
            } finally {
                coffeeMachine.release()
                logger.info { "${Thread.currentThread().name} released the coffee machine." }
            }
        } else {
            logger.warn { "Coffee machine is busy. Please wait." }
        }
    }
}
