package com.motycka.edu.coffeeshop

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.delay

private val logger = KotlinLogging.logger {}

class OrderGenerator(private val coffeeShop: CoffeeShop) {
    suspend fun generateOrders() {
        while (coffeeShop.isOpen()) {
            val order = MenuItem.entries.random()
            coffeeShop.placeOrder(order)
            delay(500)
        }
        logger.info { "Order generation stopped. Shop is closing." }
    }
}
