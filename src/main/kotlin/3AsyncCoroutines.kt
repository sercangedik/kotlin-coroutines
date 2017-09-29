import kotlinx.coroutines.experimental.*
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) = runBlocking<Unit> {
    //runSomething()
    //runWithAsync()
    //runWithLazyAsync()
    //asyncStyle()
}

suspend private fun runSomething() {
    val time = measureTimeMillis {
        val one = doSomethingUsefulOne()
        val two = doSomethingUsefulTwo()
        println("The answer is ${one + two}")
    }
    println("Completed in $time ms")
}


suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}

suspend fun runWithAsync() {
    val time = measureTimeMillis {
        val one = async(CommonPool) { doSomethingUsefulOne() }
        val two = async(CommonPool) { doSomethingUsefulTwo() }
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
}

suspend fun runWithLazyAsync(){
    val time = measureTimeMillis {
        val one = async(CommonPool, CoroutineStart.LAZY) { doSomethingUsefulOne() }
        val two = async(CommonPool, CoroutineStart.LAZY) { doSomethingUsefulTwo() }
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
}

fun asyncSomethingUsefulOne() = async(CommonPool) {
    doSomethingUsefulOne()
}

fun asyncSomethingUsefulTwo() = async(CommonPool) {
    doSomethingUsefulTwo()
}

suspend fun asyncStyle() {
    val time = measureTimeMillis {
        val one = asyncSomethingUsefulOne()
        val two = asyncSomethingUsefulTwo()
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
}