package net.intari.AndroidToolboxCore.Extensions

import net.intari.CustomLogger.CustomLog

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 28.01.2018.
 * Kotlin-style support for CustomLogger
 * Some ideas from KTX logging library
 */

/**
* A simple logging utility class which caches its tags.
* @param tag name of the logger included in log tags. Proceeded with a prefix of the specified log type.
*/
open class Logger(
        open val tag: String) {
    inline operator fun invoke(message: () -> String) {
        CustomLog.i(tag,message())
    }

    inline operator fun invoke(cause: Throwable) {
        CustomLog.logException(tag,cause)
    }
    inline operator fun invoke(cause: Exception) {
        CustomLog.logException(tag,cause)
    }
    inline fun exception(t: Throwable) {
        CustomLog.logException(tag,t)
    }
    inline fun exception(t: Exception) {
        CustomLog.logException(tag,t)
    }

    inline fun info(message: () -> String) {
        CustomLog.i(tag,message())
    }
    inline fun i(message: () -> String) {
        CustomLog.i(tag,message())
    }

    inline fun l(message: () -> String) {
        CustomLog.l(tag,message())
    }
    inline fun log(message: () -> String) {
        CustomLog.l(tag,message())
    }
    inline fun debug(message: () -> String) {
        CustomLog.d(tag,message())
    }
    inline fun d(message: () -> String) {
        CustomLog.d(tag,message())
    }
    inline fun v(message: () -> String) {
        CustomLog.v(tag,message())
    }
    inline fun verbose(message: () -> String) {
        CustomLog.v(tag,message())
    }
    inline fun w(message: () -> String) {
        CustomLog.w(tag,message())
    }
    inline fun warning(message: () -> String) {
        CustomLog.w(tag,message())
    }

    inline fun e(message: () -> String) {
        CustomLog.e(tag,message())
    }
    inline fun error(message: () -> String) {
        CustomLog.e(tag,message())
    }
    inline fun mark(message: () -> String) {
        CustomLog.logMark(message())
    }
}

/**
 * Allows to create a [Logger] instance for a specific class. Note that loggers are not cached - each time this method
 * is called, a new instance of [Logger] is created, so it is advised to use loggers in companion objects.
 * @return a new instance of [Logger] which contains the passed class name in log tags.
 */
inline fun <reified T : Any> logger(): Logger = Logger(T::class.java.name)