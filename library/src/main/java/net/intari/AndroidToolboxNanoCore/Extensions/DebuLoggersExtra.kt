package net.intari.AndroidToolboxNanoCore.Extensions

/**
 * Created by Dmitriy Kazimirov on 2019-07-20.
 */
interface supportsDebugLogging {
    fun toShortString():String
}

fun Any?.debugDescription():String {
    if (this==null) {
        return "<null>"
    } else {
        return when (this) {
            is supportsDebugLogging -> (this as supportsDebugLogging).toShortString()
            else -> this.toString()
        }
    }
}