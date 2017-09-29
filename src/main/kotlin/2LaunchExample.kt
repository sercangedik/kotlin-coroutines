import kotlinx.coroutines.experimental.*

fun main(args: Array<String>) { //<-
    useSuspend()
}

private fun useSuspend() {
    val job = launch(CommonPool) {
        print("launched useSuspend function")
    }

   // job.join()
}

suspend fun launchWithDelay() {
    launch(CommonPool) {
        // create new coroutine in common thread pool
        delay(1000L)
        println("World!")
    }
    println("Hello,") // main coroutine continues while child is delayed
    //delay(2000L) // non-blocking delay for 2 seconds to keep JVM alive
}


suspend fun launchWithJoin() {
    val job = launch(CommonPool) {
        // create new coroutine in common thread pool
        delay(1000L)
        println("World!")
    }
    println("Hello,") // main coroutine continues while child is delayed
    job.join()
}

suspend fun cancellingCoroutine() {
    val job = launch(CommonPool) {
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancel() // cancels the job
    delay(1300L) // delay a bit to ensure it was cancelled indeed
    println("main: Now I can quit.")
}

suspend fun computingNoCancelling() {
    val startTime = System.currentTimeMillis()
    val job = launch(CommonPool) {
        var nextPrintTime = startTime
        var i = 0
        while (i < 10) { // computation loop, just wastes CPU
            // print a message twice a second
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancel() // cancels the job
    delay(1300L) // delay a bit to see if it was cancelled....
    println("main: Now I can quit.")
}

suspend fun computingCancelling() {
    val startTime = System.currentTimeMillis()
    val job = launch(CommonPool) {
        var nextPrintTime = startTime
        var i = 0
        while (isActive) { // cancellable computation loop
            // print a message twice a second
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancel() // cancels the job
    delay(1300L) // delay a bit to see if it was cancelled....
    println("main: Now I can quit.")
}


fun log(msg : String) {
    println("[${Thread.currentThread().name}] $msg")
}

suspend fun customThreadPool() {
    val ctx = newSingleThreadContext("Ctx1")
    //you can use ctx instead of CommonPool

    val job = launch(ctx) {
        // create new coroutine in common thread pool
        delay(1000L)
        log("World!")
    }
    log("Hello,") // main coroutine continues while child is delayed
    job.join()
}

suspend fun jumpingBetweenThreads() {
    val ctx1 = newSingleThreadContext("Ctx1")
    val ctx2 = newSingleThreadContext("Ctx2")
    val job = launch(ctx1) {
        log("Started in ctx1")
        run(ctx2) {
            log("Working in ctx2")
        }
        log("Back to ctx1")
    }

    job.join()
}



