package net.intari.AndroidToolboxNanoCore.Extensions

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by Dmitriy Kazimirov on 2019-07-20.
 */

private val JSON_INDENT = 2

fun String.logJson(logger: Logger) {
    var json = this
    if (json.isNullOrEmpty()) {
        logger.d { "Empty/Null json content" }
        return
    }
    try {
        json = json.trim { it <= ' ' }
        if (json.startsWith("{")) {
            val jsonObject = JSONObject(json)
            val message = jsonObject.toString(JSON_INDENT)
            logger.d { message }
            return
        }
        if (json.startsWith("[")) {
            val jsonArray = JSONArray(json)
            val message = jsonArray.toString(JSON_INDENT)
            logger.d { message }
            return
        }
        logger.e {  "Invalid Json:${json}" }
    } catch (e: JSONException) {
        logger.e { "Error parsing json:${json}" }
        logger.logException(e)
        this.logException(e,"Error parsing json:${json}")
    }
}