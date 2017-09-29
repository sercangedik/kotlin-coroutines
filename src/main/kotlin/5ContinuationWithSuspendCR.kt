import kotlinx.coroutines.experimental.*
import java.util.*
import kotlin.coroutines.experimental.suspendCoroutine

fun main(args: Array<String>) = runBlocking {
    val deferred = async(CommonPool) {
        try {
          runSuspendCR()
        } catch (e: Exception) {
            println("Catching exception $e")
        }
    }

    println("Scanned value : ${deferred.await()}")
}

suspend fun runSuspendCR() : Int = suspendCoroutine { continuation ->
    suspendCR {
        //continuation.resume(it)
        continuation.resumeWithException(Exception("Ouch!"))
    }
}


fun suspendCR(returnValue: (Int) -> Unit) {
    //like a http req...
    with(Scanner(System.`in`)) {
        returnValue(nextInt() + 1)
    }
}