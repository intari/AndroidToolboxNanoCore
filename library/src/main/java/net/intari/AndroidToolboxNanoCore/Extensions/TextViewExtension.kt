package net.intari.AndroidToolboxNanoCore.Extensions

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView

/**
 * Created by Dmitriy Kazimirov on 11/10/2018.
 */

fun TextView.makeTextLink(str: String, underlined: Boolean, color: Int?, action: (() -> Unit)? = null) {
    val spannableString = SpannableString(this.text)
    val textColor = color ?: this.currentTextColor
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(textView: View) {
            action?.invoke()
        }
        override fun updateDrawState(drawState: TextPaint) {
            super.updateDrawState(drawState)
            drawState.isUnderlineText = underlined
            drawState.color = textColor
        }
    }
    val index = spannableString.indexOf(str)
    spannableString.setSpan(clickableSpan, index, index + str.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    this.text = spannableString
    this.movementMethod = LinkMovementMethod.getInstance()
    this.highlightColor = Color.TRANSPARENT
}