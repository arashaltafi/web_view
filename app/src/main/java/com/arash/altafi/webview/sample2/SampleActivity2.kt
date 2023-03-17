package com.arash.altafi.webview.sample2

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import com.arash.altafi.webview.databinding.ActivitySample2Binding

class SampleActivity2 : AppCompatActivity() {

    private val binding by lazy {
        ActivitySample2Binding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    private fun init() = binding.apply {
        btnCustomTab.setOnClickListener {
            val url = "https://arashaltafi.ir/"
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent: CustomTabsIntent = builder.build()
            customTabsIntent.launchUrl(this@SampleActivity2, Uri.parse(url))
        }

        btnOpenBrowser.setOnClickListener {
            val url = "https://arashaltafi.ir/"
            val intent1 = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent1)
        }
    }

}