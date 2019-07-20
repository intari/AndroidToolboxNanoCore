package net.intari.AndroidToolboxNanoCore

import android.os.Build
import android.text.TextUtils

/**
 * Created by Dmitriy Kazimirov on 2019-07-20.
 */
object DeviceInformation {

    val androidApiLevel: Int?
        get() {
            return Build.VERSION.SDK_INT
        }
    val androidVersionName: String?
        get() {
            val androidApiLevel= Build.VERSION.SDK_INT
            val codename= Build.VERSION.CODENAME
            when (androidApiLevel) {
                Build.VERSION_CODES.BASE -> return "Android 1.0"
                Build.VERSION_CODES.BASE_1_1 -> return "Android 1.1"
                Build.VERSION_CODES.CUPCAKE  -> return "Android 1.5"
                Build.VERSION_CODES.DONUT  -> return "Android 1.6"
                Build.VERSION_CODES.ECLAIR  -> return "Android 2.0"
                Build.VERSION_CODES.ECLAIR_0_1  -> return "Android 2.0.1"
                Build.VERSION_CODES.ECLAIR_MR1  -> return "Android 2.1"
                Build.VERSION_CODES.FROYO  -> return "Android 2.2"
                Build.VERSION_CODES.GINGERBREAD  -> return "Android 2.3"
                Build.VERSION_CODES.GINGERBREAD_MR1  -> return "Android 2.3"
                Build.VERSION_CODES.HONEYCOMB  -> return "Android 3.0"
                Build.VERSION_CODES.HONEYCOMB_MR1  -> return "Android 3.1"
                Build.VERSION_CODES.HONEYCOMB_MR2  -> return "Android 3.2"
                Build.VERSION_CODES.ICE_CREAM_SANDWICH  -> return "Android 4.0"
                Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1  -> return "Android 4.0"
                Build.VERSION_CODES.JELLY_BEAN  -> return "Android 4.1"
                Build.VERSION_CODES.JELLY_BEAN_MR1  -> return "Android 4.2"
                Build.VERSION_CODES.JELLY_BEAN_MR2  -> return "Android 4.3"
                Build.VERSION_CODES.KITKAT  -> return "Android 4.4"
                Build.VERSION_CODES.KITKAT_WATCH  -> return "Android 4.4W" //should not happen but...
                Build.VERSION_CODES.LOLLIPOP  -> return "Android 5.0"
                Build.VERSION_CODES.LOLLIPOP_MR1  -> return "Android 5.1"
                Build.VERSION_CODES.M  -> return "Android 6.0"
                Build.VERSION_CODES.N  -> return "Android 7.0"
                Build.VERSION_CODES.N_MR1  -> return "Android 7.1"
                Build.VERSION_CODES.O -> return "Android 8.0"
                Build.VERSION_CODES.O_MR1  -> return "Android 8.1"
                Build.VERSION_CODES.P  -> return "Android 9.0"
                Build.VERSION_CODES.CUR_DEVELOPMENT  -> return "Current development version of Android"
                else -> return "Unknown Android version"
            }
        }

    val deviceName: String?
        get() {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            return if (model.startsWith(manufacturer)) {
                capitalize(model)
            } else capitalize(manufacturer) + " " + model
        }

    private fun capitalize(str: String): String? {
        if (TextUtils.isEmpty(str)) {
            return str
        }
        val arr = str.toCharArray()
        var capitalizeNext = true

        val phrase = StringBuilder()
        for (c in arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c))
                capitalizeNext = false
                continue
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true
            }
            phrase.append(c)
        }

        return phrase.toString()
    }
}