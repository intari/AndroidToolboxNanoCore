package net.intari.AndroidToolboxNanoCore.Extensions

import kotlinx.coroutines.*

/**
 * Created by Dmitriy Kazimirov on 11/10/2018.
 */


fun <A, B>List<A>.pmap(f: suspend (A) -> B): List<B> = runBlocking {
    map { GlobalScope.async(Dispatchers.IO) { f(it) } }.map { it.await() }
}


fun <A> List<A>.logDebug(name:String,logger:Logger) {

    logger.d { "${name}, ${this.size} elements,they are:"}
    for (item in this ) {
        logger.d { "${item.toString()}" }
    }
}

fun <A> List<A>.logInfo(name:String,logger:Logger) {

    logger.info { "${name}, ${this.size} elements,they are:"}
    for (item in this ) {
        logger.d { "${item.toString()}" }
    }
}
