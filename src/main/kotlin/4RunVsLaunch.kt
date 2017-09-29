import kotlinx.coroutines.experimental.*

fun main(args: Array<String>) = runBlocking {

    println("started")

    val a = run(CommonPool) {
        delay(3000)
        42
    }

    println("a value : $a")

    val b = launch(CommonPool) {
        delay(3000)
        42
    }

    println(b.join())
}