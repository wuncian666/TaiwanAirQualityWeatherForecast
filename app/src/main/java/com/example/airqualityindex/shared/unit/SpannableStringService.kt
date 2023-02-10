package com.example.airqualityindex.shared.unit

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.text.style.SuperscriptSpan
import android.widget.TextView

class SpannableStringService {

    fun setSuperscriptText(string: String, textView: TextView) {
        val spannableStringBuilder = SpannableStringBuilder(string)

        val superscriptSpan = SuperscriptSpan()
        spannableStringBuilder.setSpan(
            superscriptSpan, string.lastIndex, string.lastIndex + 1,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val relativeSizeSpan = RelativeSizeSpan(.5f)
        spannableStringBuilder.setSpan(
            relativeSizeSpan, string.lastIndex, string.lastIndex + 1,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        textView.text = spannableStringBuilder
    }
}