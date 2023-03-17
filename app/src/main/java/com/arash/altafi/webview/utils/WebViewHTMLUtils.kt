package com.arash.altafi.webview.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

object WebViewHTMLUtils {

    private val tagListA1 by lazy {
        arrayListOf<String>().apply {
            add("font")
            add("style")
        }
    }

    fun getNewWebView(context: Context) = WebView(context).apply {
        webChromeClient = WebChromeClient()
        webViewClient = WebViewClient()

        setLayerType(View.LAYER_TYPE_HARDWARE, null)
        isVerticalScrollBarEnabled = false
        isHorizontalScrollBarEnabled = false
        settings.apply {
            javaScriptEnabled = false
            domStorageEnabled = true
            loadsImagesAutomatically = true
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun getNewWebViewOpenInBrowser(context: Context) = WebView(context).apply {
        settings.apply {
            javaScriptEnabled = true
        }
    }

    private fun clearHTML(content: String): String {
        var newContact = content

        tagListA1.forEach {
            newContact = newContact.replace(
                "(<${it}[^>]*>)|(<\\/${it}>)".toRegex(), ""
            )
        }

        val patternRemoveColors =
            "(?:#|0x)(?:[A-f0-9]{3}|[A-f0-9]{6}|[A-f0-9]{8})\\b|(?:rgb|hsl)a?\\([^\\)]*\\)"
        newContact = newContact.replace(patternRemoveColors.toRegex(), "inherit")

        return newContact
    }

    fun compatHTML(
        context: Context, content: String,
        backgroundColor: String? = null,
        textColor: String? = null
    ): String {
        var html = "<style>img{max-width:100%;height:auto;} iframe{width:100%;}</style>"
        html += clearHTML(content)

        val fontRes1 =
            "@font-face {font-family:IRANSansMobileFaNum Light;" +
                    " src: url(\"file:///android_asset/font/font_light.ttf\")}"
        val fontRes2 =
            "@font-face {font-family:IRANSansMobile Light; " +
                    "src: url(\"file:///android_asset/font/font_light_latin_digits.ttf\")}"
        val fontStyle1 =
            "body { font-family:IRANSansMobileFaNum Light; " +
                    "font-size: small; text-align: right;"
        val fontStyle2 =
            " font-family:IRANSansMobile Light; font-size: small; text-align: right; "

        val body = fontStyle1 + "background-color: ${
            backgroundColor ?: context.getAttrColor(android.R.attr.colorBackground).toHexColor()
        }; color: ${
            textColor ?: context.getAttrColor(android.R.attr.textColor).toHexColor()
        };}"

        val link = if (context.isDarkTheme())
            "a:link, a:visited { $fontStyle2 color: greenyellow; }" +
                    " a:hover, a:active { $fontStyle2 color: gainsboro; }"
        else "a:link, a:visited { $fontStyle2 } a:hover, a:active { $fontStyle2 }"

        val css = fontRes1 + fontRes2 + body + link

        val first = "<html><head><style type=\"text/css\">$css</style></head><body dir=\"rtl\">"
        val second = "</body></html>"
        return first + html + second
    }
}