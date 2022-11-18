package com.arash.altafi.webview

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var url: String
    private val cookieManager: CookieManager = CookieManager.getInstance()

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
        val webView = WebView(this)

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

    private fun createCookie(webView: WebView, url: String) {
        val cookieName = "sessionName"
        val cookieValue = "sessionValue"
        val cookieTime = 36000

        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)
        cookieManager.setAcceptCookie(true)
        val date = Date()
        date.time = date.time + cookieTime
        val expires = "; expires=" + date.toGMTString()
        val cookieString = "$cookieName=$cookieValue$expires; path=/"
        cookieManager.setCookie(url, cookieString)
        cookieManager.flush()
    }

    private fun handleBackPress(webView: WebView) {
        webView.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action === KeyEvent.ACTION_DOWN) {
                    when (keyCode) {
                        KeyEvent.KEYCODE_BACK -> if (webView.canGoBack()) {
                            webView.goBack()
                            return true
                        }
                    }
                }
                return false
            }
        })

    }

}