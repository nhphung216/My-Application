package com.example.myapplication

import android.os.Bundle
import com.example.myapplication.databinding.ActivityScrapeTicketBinding

class ScrapeTicketActivity : BaseActivity() {

    private lateinit var binding: ActivityScrapeTicketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScrapeTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpDrawView()
    }

    private fun setUpDrawView() {

    }
}