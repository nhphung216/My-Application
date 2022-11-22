package com.example.myapplication

import android.os.Bundle
import com.example.myapplication.databinding.ActivityScrapeTicketBinding
import com.example.myapplication.draw.ScrapeTicketView
import java.text.DecimalFormat
import java.text.NumberFormat

class ScrapeTicketActivity : BaseActivity() {

    private lateinit var binding: ActivityScrapeTicketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScrapeTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.scrapeView.initScrapeView(
            R.drawable.ic_background, R.drawable.ic_foreground, R.raw.scratcheffect
        )
        binding.scrapeView.listener = object : ScrapeTicketView.ScrapeViewListener {
            override fun onPercentScratchOut(percent: Double) {
                val formatter: NumberFormat = DecimalFormat("#0.00")
                binding.percent.text = "${formatter.format(percent)}%"
            }
        }
    }
}