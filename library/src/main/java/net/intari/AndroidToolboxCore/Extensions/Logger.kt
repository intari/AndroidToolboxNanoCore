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


    /**
     * Logs exception
     * @param cause
     */
    inline operator fun invoke(cause: Throwable) {
        CustomLog.logException(tag,cause)
    }
    /**
     * Logs exception
     * @param cause
     */
    inline operator fun invoke(cause: Exception) {
        CustomLog.logException(tag,cause)
    }

    /**
     * Logs exception
     * @param cause
     */
    inline fun exception(t: Throwable) {
        CustomLog.logException(tag,t)
    }

    /**
     * Logs exception
     * @param cause
     */
    inline fun exception(t: Exception) {
        CustomLog.logException(tag,t)
    }

    /**
     * Logs message at info level
     * @param message labmda to be evaluated for message text
     */
    inline operator fun invoke(message: () -> String) {
        CustomLog.i(tag,message())
    }

    /**
     * Logs message at info level
     * @param message labmda to be evaluated for message text
     */
    inline fun info(message: () -> String) {
        CustomLog.i(tag,message())
    }

    /**
     * Logs message at info level
     * @param message labmda to be evaluated for message text
     */
    inline fun i(message: () -> String) {
        CustomLog.i(tag,message())
    }

    /**
     * Logs message at log level
     * @param message labmda to be evaluated for message text
     */
    inline fun l(message: () -> String) {
        CustomLog.l(tag,message())
    }

    /**
     * Logs message at log level
     * @param message labmda to be evaluated for message text
     */
    inline fun log(message: () -> String) {
        CustomLog.l(tag,message())
    }

    /**
     * Logs message at debug level
     * @param message labmda to be evaluated for message text
     */
    inline fun debug(message: () -> String) {
        CustomLog.d(tag,message())
    }

    /**
     * Logs message at debug level
     * @param message labmda to be evaluated for message text
     */
    inline fun d(message: () -> String) {
        CustomLog.d(tag,message())
    }

    /**
     * Logs message at verbose level
     * @param message labmda to be evaluated for message text
     */
    inline fun v(message: () -> String) {
        CustomLog.v(tag,message())
    }

    /**
     * Logs message at verbose level
     * @param message labmda to be evaluated for message text
     */
    inline fun verbose(message: () -> String) {
        CustomLog.v(tag,message())
    }

    /**
     * Logs message at warning level
     * @param message labmda to be evaluated for message text
     */
    inline fun w(message: () -> String) {
        CustomLog.w(tag,message())
    }

    /**
     * Logs message at warning level
     * @param message labmda to be evaluated for message text
     */
    inline fun warning(message: () -> String) {
        CustomLog.w(tag,message())
    }

    /**
     * Logs message at error level
     * @param message labmda to be evaluated for message text
     */
    inline fun e(message: () -> String) {
        CustomLog.e(tag,message())
    }

    /**
     * Logs message at error level
     * @param message labmda to be evaluated for message text
     */
    inline fun error(message: () -> String) {
        CustomLog.e(tag,message())
    }

    /**
     * Put mark in log stream (see NSLogger's docs)
     * @param message labmda to be evaluated for mark text
     */
    inline fun mark(message: () -> String) {
        CustomLog.logMark(message())
    }
}

/**
 * Allows to create a [Logger] instance for a specific class. Note that loggers are not cached - each time this method
 * is called, a new instance of [Logger] is created, so it is advised to use loggers in companion objects.
 * tag will be T's 'simple' name 
 * @return a new instance of [Logger] which contains the passed class name in log tags.
 */
inline fun <reified T : Any> logger(): Logger = Logger(T::class.java.simpleName)
/**
 * Allows to create a [Logger] instance for a specific class. Note that loggers are not cached - each time this method
 * is called, a new instance of [Logger] is created, so it is advised to use loggers in companion objects.
 * tag will be T's full class name
 * @return a new instance of [Logger] which contains the passed class name in log tags.
 */
inline fun <reified T : Any> loggerWithFullClassName(): Logger = Logger(T::class.java.name)
