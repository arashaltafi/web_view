package com.arash.altafi.webview.sample1

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.arash.altafi.webview.databinding.ActivitySample1Binding
import com.arash.altafi.webview.utils.WebViewHTMLUtils
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class SampleActivity1 : AppCompatActivity() {

    private val binding by lazy {
        ActivitySample1Binding.inflate(layoutInflater)
    }

    private lateinit var url: String
    private val cookieManager: CookieManager = CookieManager.getInstance()
    private lateinit var webView: WebView
    private var afterLollipop: ValueCallback<Array<Uri>>? = null
    private var mUploadMessage: ValueCallback<Uri>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = "Arash Altafi"

        url = "https://arashaltafi.ir/"
        init(url)
        handleBackPress()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun init(url: String) = binding.apply {
        webView = WebViewHTMLUtils.getNewWebView(this@SampleActivity1).apply {
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                }

                //prevent open browser out of app
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    request?.url?.let {
                        val mUrl = it.toString()
                        when {
                            mUrl.startsWith("mailto:") -> {
                                startActivity(Intent(Intent.ACTION_SENDTO, Uri.parse(mUrl)))
                            }
                            mUrl.startsWith("tel:") -> {
                                startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(mUrl)))
                            }
                            else -> view?.loadUrl(mUrl)
                        }
                    }
                    return true
                }

            }

            val getFileResultLauncher = registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) { ar: ActivityResult ->
                val intent: Intent? = ar.data
                val result =
                    if (intent == null || ar.resultCode != RESULT_OK) null else intent.data
                if (mUploadMessage != null) {
                    mUploadMessage!!.onReceiveValue(result)
                } else if (afterLollipop != null) {
                    afterLollipop!!.onReceiveValue(
                        WebChromeClient.FileChooserParams.parseResult(
                            ar.resultCode,
                            intent
                        )
                    )
                    afterLollipop = null
                }
                mUploadMessage = null
            }

            webChromeClient = object : WebChromeClient() {
                override fun onShowFileChooser(
                    webView: WebView?,
                    filePathCallback: ValueCallback<Array<Uri>>,
                    fileChooserParams: FileChooserParams
                ): Boolean {
                    afterLollipop = filePathCallback
                    getFileResultLauncher.launch(fileChooserParams.createIntent())
                    return true
                }
            }

            settings.apply {
                javaScriptEnabled = true
            }

            createCookie()
            loadUrl(url)
        }.also {
            fWebView.addView(it)
            it.setDownloadListener { s, _, _, _, _ ->
                val uri = Uri.parse(s)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
        }
    }

    private fun closeWeb() {
        if (::webView.isInitialized) {
            webView.apply {
                clearHistory()
                clearCache(true)
                clearFormData()
                destroy()
            }
            cookieManager.removeAllCookies(null)
//            cookieManager.removeExpiredCookie()
            cookieManager.flush()
        }
    }

    private fun createCookie() {
        val cookieName = "sessionName"
        val cookieValue = "sessionValue"
        val cookieTime = 36000

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.HOUR, (cookieTime / 1000.0 / 3600.0).roundToInt())
        val dateFormat = SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss z", Locale.US)
        dateFormat.timeZone = TimeZone.getTimeZone("GMT")
        val expires: String = dateFormat.format(calendar.time)
        cookieManager.setCookie(url, "$cookieName=$cookieValue;expires=$expires")
        cookieManager.setAcceptCookie(true)
        cookieManager.flush()
    }

    private fun handleBackPress() {
        if (::webView.isInitialized) {
            webView.setOnKeyListener(object : View.OnKeyListener {
                override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                    if (event.action == KeyEvent.ACTION_DOWN) {
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

    override fun onDestroy() {
        super.onDestroy()
        closeWeb()
    }

}