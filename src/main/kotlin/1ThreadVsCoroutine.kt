import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

fun main(args: Array<String>) {

}

private fun launchCoroutine() {
    val c = AtomicInteger()

    for (i in 1..1_000_000)
        launch(CommonPool) {
            c.addAndGet(i)
        }

    println("CR Completed.")
}

private fun runThread() {
    val c = AtomicInteger()

    for (i in 1..1_000_000)
        thread(start = true) {
            c.addAndGet(i)
        }

    println("Normal completed")
}