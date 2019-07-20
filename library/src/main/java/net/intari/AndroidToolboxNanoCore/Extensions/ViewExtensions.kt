package net.intari.AndroidToolboxNanoCore.Extensions

import android.content.res.Resources
import android.support.v4.app.Fragment
import android.view.View

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 18.11.2017.
 */

var View.visible: Boolean
    get () = visibility ==View.VISIBLE
    set (value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

fun View.isVisible(): Boolean = visibility == View.VISIBLE

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInvisible() {
    visibility = View.INVISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}

fun View.makeVisibleOrGone(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.makeVisibleOrInvisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.INVISIBLE
}


//returns resourceName and tag(if here)
fun View.resourceName():String {
    val sb=StringBuilder()
    if (id != View.NO_ID) {
        val r = resources
        val hasPackage = id.ushr(24) != 0 // Resources.hasPackage is @hide so just copy
        if (id > 0 && hasPackage && r != null) {
            try {
                val pkgname: String
                when (id and -0x1000000) {
                    0x7f000000 -> pkgname = "app"
                    0x01000000 -> pkgname = "android"
                    else -> pkgname = r.getResourcePackageName(id)
                }
                val typename = r.getResourceTypeName(id)
                val entryname = r.getResourceEntryName(id)
                sb.append(" ")
                sb.append(pkgname)
                sb.append(":")
                sb.append(typename)
                sb.append("/")
                sb.append(entryname)
                this.tag?.let {
                    sb.append(",tag:${it}")
                }
            } catch (e: Resources.NotFoundException) {
            }
        }
    }
    return sb.toString()
}



/**
 * Runs on UI thread if it does make sense at all (activity is here, etc
 * based on fun <T: Fragment> AnkoAsyncContext<T>.supportFragmentUiThread(f: (T) -> Unit): Boolean from Anko
 * @param callback - callback
 * @return - false if was clear callback will NOT be run
 */
fun <T: Fragment> T.tryRunOnUiThread(f: (T) -> Unit): Boolean {
    if (this.isDetached) return false
    val activity = this.activity ?: return false
    if (this.view==null) return false
    activity.runOnUiThread { f(this) }
    return true
}

fun <T: Fragment> T.toShortString(): String {
    return "s.${this.javaClass.name}(isDetached:${this.isDetached}, activity:${if (this.activity!=null) "present" else "not present"}), view:${if (this.view!=null) "not null" else "null"})"
}

inline fun Fragment.runOnUiThreadIfActivity(crossinline f: () -> Unit) {
    requireActivity().runOnUiThread { f() }
}
