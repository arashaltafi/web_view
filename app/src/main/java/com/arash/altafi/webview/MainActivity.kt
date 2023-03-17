package com.arash.altafi.webview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arash.altafi.webview.databinding.ActivityMainBinding
import com.arash.altafi.webview.sample1.SampleActivity1
import com.arash.altafi.webview.sample2.SampleActivity2

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    private fun init() = binding.apply {
        btnSample1.setOnClickListener {
            startActivity(Intent(this@MainActivity, SampleActivity1::class.java))
        }
        btnSample2.setOnClickListener {
            startActivity(Intent(this@MainActivity, SampleActivity2::class.java))
        }
    }

}