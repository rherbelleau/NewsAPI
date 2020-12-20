package com.example.newsapi

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : AppCompatActivity() {

    lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        queue = Volley.newRequestQueue(this)

        webPage.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, weburl: String) {
                progressBarWeb.isVisible = false
            }
        }

        intent.getStringExtra("url")?.let { webPage.loadUrl(it) }
    }
}
