package net.intari.AndroidToolboxNanoCore.Extensions

import net.intari.AndroidToolboxNanoCore.Constants

/**
 * Created by Dmitriy Kazimirov on 2019-07-20.
 */

fun Long.asDataSize():String {
    if (this<Constants.BYTES_PER_MB) {
        return "${this/Constants.BYTES_PER_KB} Kb"
    } else {
        if (this<Constants.BYTES_PER_GB) {
            return "${this/Constants.BYTES_PER_MB} Mb"
        } else {
            return "${this/Constants.BYTES_PER_GB} Gb"
        }
    }
}