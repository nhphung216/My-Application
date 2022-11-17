package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webView.setOnClickListener {
            startActivity(Intent(this, WebViewActivity::class.java))
        }

        binding.draw.setOnClickListener {
            startActivity(Intent(this, DrawActivity::class.java))
        }

        binding.scrapeTicket.setOnClickListener {
            startActivity(Intent(this, ScrapeTicketActivity::class.java))
        }
    }
}