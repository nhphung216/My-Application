package com.example.myapplication

import android.net.http.SslError
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.myapplication.databinding.ActivityWebviewBinding

class WebViewActivity : BaseActivity() {

    private lateinit var binding: ActivityWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webView.webViewClient = object : WebViewClient() {
            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                Log.e("WebView", "onLoadResource: $url")
            }

            override fun onReceivedSslError(
                view: WebView?, handler: SslErrorHandler?, error: SslError?
            ) {
                Log.e("WebView", "onReceivedSslError: ${error.toString()}")
                Toast.makeText(this@WebViewActivity, error.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.setSupportZoom(true)
        binding.webView.settings.domStorageEnabled = true
        binding.webView.loadUrl("https://whatwebcando.today/vibration.html")

        binding.load.setOnClickListener {
            val link = binding.input.text.toString().trim()
            if (!TextUtils.isEmpty(link)) {
                binding.webView.loadUrl(link)
            }
        }
    }
}