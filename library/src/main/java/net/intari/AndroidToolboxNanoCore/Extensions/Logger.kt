package net.intari.AndroidToolboxNanoCore.Extensions

import android.content.Context
import net.intari.CustomLogger.CustomLog

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 28.01.2018.
 * Kotlin-style support for CustomLogger
 * Some ideas from KTX logging library
 */

/**
* A simple logging utility class which caches its tags.
* @param tag name of the logger included in log tags.
*/
open class Logger(
        open val tag: String) {


    /**
     * Sets user email
     * @param userEmail labmda to be evaluated for user email
     */
    inline fun userEmail(userEmail: () -> String) = CustomLog.setUserEmail(userEmail())

    /**
     * Sets user name
     * @param labmda to be evaluated for user name
     */
    inline fun userName(userName: () -> String) = CustomLog.setUserName(userName())

    /**
     * Sets user identifier
     * @param userIdentifier labmda to be evaluated for userIdentifier text
     */
    inline fun userIdentifier(userIdentifier: () -> String) = CustomLog.setUserIdentifier(userIdentifier())



    /**
     * Support for https://docs.fabric.io/android/crashlytics/enhanced-reports.html#custom-keys
     * Crashlytics supports a maximum of 64 key/value pairs.
     * Once you reach this threshold, additional values are not saved. Each key/value pair can be up to 1 KB in size.
     * @param key
     * @param value
     */
    inline fun crashlyticsBool(key:String, value:() -> Boolean) = CustomLog.setBool(key,value())


    /**
     * Support for https://docs.fabric.io/android/crashlytics/enhanced-reports.html#custom-keys
     * Crashlytics supports a maximum of 64 key/value pairs.
     * Once you reach this threshold, additional values are not saved. Each key/value pair can be up to 1 KB in size.
     * @param key
     * @param value
     */
    inline fun crashlyticsDouble(key:String, value:() -> Double) = CustomLog.setDouble(key,value())


    /**
     * Support for https://docs.fabric.io/android/crashlytics/enhanced-reports.html#custom-keys
     * Crashlytics supports a maximum of 64 key/value pairs.
     * Once you reach this threshold, additional values are not saved. Each key/value pair can be up to 1 KB in size.
     * @param key
     * @param value
     */
    inline fun crashlyticsFloat(key:String, value:() -> Float) = CustomLog.setFloat(key,value())

    /**
     * Support for https://docs.fabric.io/android/crashlytics/enhanced-reports.html#custom-keys
     * Crashlytics supports a maximum of 64 key/value pairs.
     * Once you reach this threshold, additional values are not saved. Each key/value pair can be up to 1 KB in size.
     * @param key
     * @param value
     */
    inline fun crashlyticsInt(key:String, value:() -> Int) = CustomLog.setInt(key,value())

    /**
     * Support for https://docs.fabric.io/android/crashlytics/enhanced-reports.html#custom-keys
     * @param key
     * @param value
     */
    inline fun crashlyticsLong(key:String, value:() -> Long) = CustomLog.setLong(key,value())

    /**
     * Support for https://docs.fabric.io/android/crashlytics/enhanced-reports.html#custom-keys
     * @param key
     * @param value
     */
    inline fun crashlyticsString(key:String, value:() -> String) = CustomLog.setString(key,value())

    /**
     * Where NSLogger.app is running
     * @param newLogHost hostname like host.domain.com
     * @param newLogPort port number
     * @return false if this function was arleady called
     */
    fun logDestination(newLogHost: String, newLogPort: Int): Boolean = CustomLog.setLogDestination(newLogHost,newLogPort)


    /**
     * Should we log exceptions to Crashlytics? (=should logException call Crashlytics
     * @param newIsLogExceptions labmda to be evaluated for newIsLogExceptions
     */
    fun logExceptions(newIsLogExceptions: () -> Boolean) = CustomLog.setLogExceptions(newIsLogExceptions())

    /**
     *  Should we use extra debugging features (like dump to logcat)
     * @param newIsDebug labmda to be evaluated for isDebug
     */
    @Deprecated("use 'useDebug'")
    fun IsDebug(newIsDebug: () -> Boolean) = CustomLog.setIsDebug(newIsDebug())

    /**
     * Should we use extra debugging features (like dump to logcat)
     * @param newIsDebug labmda to be evaluated for isDebug
     */
    fun useDebug(newIsDebug: () -> Boolean) = IsDebug(newIsDebug)

    /**
     * Is it ok to use Crashlytics? (not currently used)
     * @param newIsLogCrashlytics
     */
    @Deprecated("use 'useCrashlytics'")
    fun logCrashlytics(newIsLogCrashlytics: () -> Boolean) = CustomLog.setLogCrashlytics(newIsLogCrashlytics())

    /**
     * Is it ok to use Crashlytics? (not currently used)
     * @param newIsLogCrashlytics
     */
    fun useCrashlytics(newIsLogCrashlytics: () -> Boolean) = logCrashlytics(newIsLogCrashlytics)

    /**
     * Sets app context to use
     * @param context labmda to be evaluated for context
     */
    @Deprecated("use 'useContext'")
    fun context(context: () -> Context) = CustomLog.setContext(context())

    /**
     * Sets app context to use
     * @param context labmda to be evaluated for context
     */
    fun useContext(context: () -> Context) = context(context)

    /**
     * Logs exception
     * @param cause
     */
    operator fun invoke(cause: Throwable) = CustomLog.logException(tag,cause)

    /**
     * Logs exception
     * @param cause
     */
    operator fun invoke(cause: Exception) = CustomLog.logException(tag,cause)

    /**
     * Logs exception
     * @param cause
     */
    fun exception(t: Throwable) = CustomLog.logException(tag,t)

    /**
     * Logs exception
     * @param cause
     */
    fun exception(t: Exception) = CustomLog.logException(tag,t)

    /**
     * Logs message at info level
     * @param message labmda to be evaluated for message text
     */
    inline operator fun invoke(message: () -> String) = CustomLog.i(tag,message())

    /**
     * Logs message at info level
     * @param message labmda to be evaluated for message text
     */
    inline fun info(message: () -> String) = CustomLog.i(tag,message())

    /**
     * Logs message at info level
     * @param message labmda to be evaluated for message text
     */
    inline fun i(message: () -> String) = CustomLog.i(tag,message())

    /**
     * Logs message at log level
     * @param message labmda to be evaluated for message text
     */
    inline fun l(message: () -> String) = CustomLog.l(tag,message())

    /**
     * Logs message at log level
     * @param message labmda to be evaluated for message text
     */
    inline fun log(message: () -> String) = CustomLog.l(tag,message())

    /**
     * Logs message at debug level
     * @param message labmda to be evaluated for message text
     */
    inline fun debug(message: () -> String) = CustomLog.d(tag,message())


    /**
     * Logs message at debug level
     * @param message labmda to be evaluated for message text
     */
    inline fun d(message: () -> String) = CustomLog.d(tag,message())

    /**
     * Logs message at verbose level
     * @param message labmda to be evaluated for message text
     */
    inline fun v(message: () -> String) = CustomLog.v(tag,message())

    /**
     * Logs message at verbose level
     * @param message labmda to be evaluated for message text
     */
    inline fun verbose(message: () -> String) = CustomLog.v(tag,message())

    /**
     * Logs message at warning level
     * @param message labmda to be evaluated for message text
     */
    inline fun w(message: () -> String) = CustomLog.w(tag,message())

    /**
     * Logs message at warning level
     * @param message labmda to be evaluated for message text
     */
    inline fun warning(message: () -> String) = CustomLog.w(tag,message())

    /**
     * Logs message at error level
     * @param message labmda to be evaluated for message text
     */
    inline fun e(message: () -> String) = CustomLog.e(tag,message())

    /**
     * Logs message at error level
     * @param message labmda to be evaluated for message text
     */
    inline fun error(message: () -> String) = CustomLog.e(tag,message())

    /**
     * Put mark in log stream (see NSLogger's docs)
     * @param message labmda to be evaluated for mark text
     */
    inline fun mark(message: () -> String) = CustomLog.logMark(message())

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
