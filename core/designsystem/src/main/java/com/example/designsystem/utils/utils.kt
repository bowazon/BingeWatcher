package com.example.designsystem.utils

import android.graphics.Typeface
import android.text.Spanned
import android.text.style.StyleSpan
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.core.text.HtmlCompat

fun getReadableTextColor(bgColor: Color): Color {
    val luminance = bgColor.luminance()
    return if (luminance > 0.5f) Color.Black else Color.White
}

@Composable
fun HtmlText(
    html: String,
    color: Color,
    style: TextStyle,
    maxLines: Int,
    overflow: TextOverflow
) {
    val spanned = remember(html) {
        HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

    Text(
        text = spanned.toAnnotatedString(),
        color = color,
        style = style,
        maxLines = maxLines,
        overflow = overflow
    )
}

fun Spanned.toAnnotatedString(): AnnotatedString {
    return buildAnnotatedString {
        append(this@toAnnotatedString.toString())

        getSpans(0, length, StyleSpan::class.java).forEach { span ->
            val start = getSpanStart(span)
            val end = getSpanEnd(span)

            when (span.style) {
                Typeface.BOLD -> addStyle(
                    SpanStyle(fontWeight = FontWeight.Bold),
                    start,
                    end
                )
                Typeface.ITALIC -> addStyle(
                    SpanStyle(fontStyle = FontStyle.Italic),
                    start,
                    end
                )
            }
        }
    }
}

