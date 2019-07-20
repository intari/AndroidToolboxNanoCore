package net.intari.AndroidToolboxNanoCore.Extensions

/**
 * Created by Dmitriy Kazimirov on 2019-07-20.
 */
fun <T> T?.getOrDie(): T = this ?: throw IllegalStateException("arg should not be null")
fun <T> T?.getOrOther(default:T): T = this ?: default

//Based on https://stackoverflow.com/a/55609263/1063214
fun getNumActiveThreadsIn():Int {
    val threads = Thread.getAllStackTraces().keys.filter {
        it.name.startsWith("CommonPool") || it.name.startsWith("ForkJoinPool")
    }
    return threads.size
}

