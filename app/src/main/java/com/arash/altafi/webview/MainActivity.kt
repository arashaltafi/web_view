package com.arash.altafi.webview

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*
import android.content.pm.PackageManager
import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import java.util.regex.Matcher
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    
    private lateinit var url : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initForWebView()
        initForFrameLayout()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initForWebView() {
        url = "https://arashaltafi.ir/"

        // load link in web view
//        web_view_2.webViewClient = WebViewClient()

        // load link in browser (intent)
        web_view_2.webChromeClient = WebChromeClient()

        web_view_2.settings.javaScriptEnabled = true
//        web_view_2.settings.javaScriptCanOpenWindowsAutomatically = true
//        web_view_2.settings.pluginState = WebSettings.PluginState.ON
//        web_view_2.settings.mediaPlaybackRequiresUserGesture = false

        // load url
        web_view_2.loadUrl(url)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initForFrameLayout() {
        url = "https://arashaltafi.ir/index2.html"
        val webView : WebView = WebView(this)

        // load link in web view
//        webView.webViewClient = WebViewClient()

        // load link in browser (intent)
//        webView.webChromeClient = WebChromeClient()
//
        webView.settings.javaScriptEnabled = true
//        webView.settings.javaScriptCanOpenWindowsAutomatically = true
//        webView.settings.pluginState = WebSettings.PluginState.ON
//        webView.settings.mediaPlaybackRequiresUserGesture = false

        // load url
        webView.loadUrl(url)

        web_view_1.addView(webView)
    }

}