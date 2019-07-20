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




fun <T:supportsDebugLogging> List<T>.toShortString(listName:String):String {
    val sb = StringBuffer()
    sb.append(listName)
    sb.append("(")
    sb.append(this.size)
    sb.append(" items):(")

    var needComma=false
    this.forEach {
        if (needComma)
            sb.append(",")
        sb.append(it.toShortString())
        needComma=true
    }
    sb.append(")")
    return sb.toString()
}


fun <T> List<T>.toString(listName:String):String {
    var sb = StringBuffer()
    sb.append(listName)
    sb.append("(")
    sb.append(this.size)
    sb.append(" items):(")

    var needComma=false
    this.forEach {
        if (needComma)
            sb.append(",")
        sb.append(it)
        needComma=true
    }
    sb.append(")")
    return sb.toString()
}
