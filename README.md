## Kotlin Concurrency Exercises

---
### Threads

#### Exercise 1: Thread

Write a simple program simulating a coffee shop:

 1. Create a thread with a name of a barista.
 2. Inside the thread log `"${Thread.currentThread().name} is making your coffee."`
 3. Wait for 5 seconds
 4. Print `"Your coffee is ready. Enjoy!"`
 5. Make sure to handle possible exceptions.

#### Exercise 2: Atomic Operations

Enhance your code to process multiple orders in parallel by multiple baristas:

1. Create an AtomicInteger variable to hold a number of orders. Set it to non-zero initial value.
2. Move the coffee making logic to a `makeCoffee` function. The function should accept one argument `orders: AtomicInteger` which is a reference to the number of orders waiting to be processed.
3. Use the `AtomicInteger` inside the `makeCoffee` function to decrease the number of orders by 1 every time a coffee is made.
4. In your `main` function, create two threads with two barista names to process the orders.

#### Exercise 3: Semaphore

Enhance the code so that the coffee shop can process only coffee one order at a time, 
as there is only one coffee machine. 

1. Use `Semaphore` to control access to the coffee machine. 
2. Pass it as parameter to the `makeCoffee` function.

---
### Coroutines

#### Exercise 1: Basic Coroutine

Take the result of your exercises and write the same logic using coroutines.

1. Use `AtomicInteger` to hold the number of orders.
2. Use `Mutex` instead of `Semaphore` to control access to the coffee machine.
3. Create a coroutine for each barista that simulates making coffee, inside the coroutine run the `makeCoffee` function.
4. Use `coffeeMachine.tryLock()` in try / catch block to ensure that only one barista can access the coffee machine at a time.
5. Use `delay(1000)` instead of `Thread.sleep(1000)` to simulate the coffee making time.
6. Call `coffeeMachine.unlock()` in the finally block to ensure that the coffee machine is released after the coffee is made.

---
#### Bonus Coroutine Exercise

In this exercise, you will implement a simulation of a coffee shop using Kotlin coroutines. 
The goal is to model a simple ordering and processing system where customers place coffee orders, and baristas prepare them in parallel.

There is a MenuItem enum with different coffee types time to prepare. You can use this example:

```kotlin
enum class MenuItem(val time: Long) {
    AMERICANO(200),
    ESPRESSO(100),
    DOUBLE_ESPRESSO(200),
    CAPUCCINO(400),
    FLAT_WHITE(400),
}
```

Your task is to implement:
 - `CoffeeShop` representing the coffee shop and processes orders.
 - `OrderGenerator` to generate coffee orders.
 - `main` function that starts the simulation .

The simulation consists of the following key components:

1. **Order Generation**
 - Orders should be randomly generated at regular intervals while the coffee shop is open.
 - The shop should only accept orders if they can be completed before closing.

2. **Order Processing**
 - The coffee shop has multiple baristas who process orders in parallel.
 - Each barista picks up one order at a time from a shared queue.
 - The barista must use a shared coffee machine, which only one person can use at a time.
 - Once an order is completed, the barista moves on to the next available order.

3. **Shop Closing Rules**
 - The coffee shop operates for a fixed duration.
 - New orders should only be accepted if they can be completed before closing.
 - Once closed, the shop stops accepting new orders, but baristas finish any pending ones.

#### CoffeeShop

 - Manages the baristas and the coffee-making process.
 - Keeps track of the opening and closing times.
 - Uses a `Channel` to queue orders.
 - Launches **multiple coroutines** (one per barista) to process orders concurrently.
 - Ensures the coffee machine is accessed safely using a `Mutex`.
 - Stops accepting orders after closing but allows baristas to finish processing remaining ones.

##### CoffeeShop should have the following functions and properties:

 - **Constructor**
   - val openDuration: Long - the duration the shop is open in unit .
   - val unit: ChronoUnit - the time unit for the open duration.
   - val baristas: List<String> - list of barista names.

 - `placeOrder` which function that takes `MenuItem` and puts it in the orders `Channel`.

 - `isOpen` method that returns true if the shop is open. It will be used by `OrderGenerator`.

 - `processOrders` method that processes orders from the `Channel` (using concurrency and spreading work across baristas).

**Processing and order should take the following steps:**
 - Barista waits for the coffee machine to be available.
 - Barista prepares the coffee; coffee preparation takes time based on the `MenuItem.time + some time for barista to "recharge".
 - Once it is past closing time, the shop should stop accepting new orders. However, it should allow baristas to finish processing any pending orders.

#### OrderGenerator
 - Generates coffee orders randomly and places order with `CoffeeShop` using the `placeOrder function.
 - Orders should be placed at regular intervals (e.g., every 500ms).
 - Should stop creating new orders when the shop is about to close.

#### main function

The main function should create and instance of `CoffeeShop` and `OrderGenerator` and start the simulation. 
Use `launch` to start both the `OrderGenerator` and `CoffeeShop` in coroutine scope.

**Ensure Proper Concurrency Handling:**

 - Use `launch` to start multiple coroutines for parallel order processing.
 - Use a `Channel` to distribute orders among baristas.
 - Use a `Mutex` to ensure only one barista uses the coffee machine at a time.
 - Use **logging** (`println` or `KotlinLogging`) to
   - show when orders are placed, prepared, and completed
   - show when the shop opens and closes
   - show when baristas are waiting for the coffee machine
