package com.example.myapplication

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import com.example.myapplication.databinding.ActivityDrawBinding

class DrawActivity : BaseActivity() {

    private lateinit var binding: ActivityDrawBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrawBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpColorPalette()
        setUpAction()
        setUpDrawView()
    }

    private fun setUpDrawView() {
        binding.drawView.setStrokeWidth(32f)
    }

    private fun setUpAction() {
        binding.imageDrawEraser.setOnClickListener {
            binding.drawView.toggleEraser()
            binding.imageDrawEraser.isSelected = binding.drawView.isEraserOn
        }
        binding.imageDrawEraser.setOnLongClickListener {
            binding.drawView.clearCanvas()
            true
        }

        binding.imageDrawUndo.setOnClickListener { binding.drawView.undo() }
        binding.imageDrawRedo.setOnClickListener { binding.drawView.redo() }
    }

    private fun setUpColorPalette() {
        binding.colorPalette.colorBlack.setOnClickListener {
            handleSelectColorPalette(binding.colorPalette.colorBlack, R.color.color_black)
        }
        binding.colorPalette.colorRed.setOnClickListener {
            handleSelectColorPalette(binding.colorPalette.colorRed, R.color.color_red)
        }
        binding.colorPalette.colorYellow.setOnClickListener {
            handleSelectColorPalette(binding.colorPalette.colorYellow, R.color.color_yellow)
        }
        binding.colorPalette.colorGreen.setOnClickListener {
            handleSelectColorPalette(binding.colorPalette.colorGreen, R.color.color_green)
        }
        binding.colorPalette.colorBlue.setOnClickListener {
            handleSelectColorPalette(binding.colorPalette.colorBlue, R.color.color_blue)
        }
        binding.colorPalette.colorPink.setOnClickListener {
            handleSelectColorPalette(binding.colorPalette.colorPink, R.color.color_pink)
        }
        binding.colorPalette.colorBrown.setOnClickListener {
            handleSelectColorPalette(binding.colorPalette.colorBrown, R.color.color_brown)
        }
    }

    private val Int.toPx: Float
        get() = (this * Resources.getSystem().displayMetrics.density)

    private fun handleSelectColorPalette(imageView: ImageView, colorRes: Int) {
        val color = ResourcesCompat.getColor(resources, colorRes, null)
        binding.drawView.setColor(color)
        scaleColorView(imageView)
    }

    private fun scaleColorView(view: View) {
        // reset scale of all views
        binding.colorPalette.colorBlack.scaleX = 1f
        binding.colorPalette.colorBlack.scaleY = 1f

        binding.colorPalette.colorRed.scaleX = 1f
        binding.colorPalette.colorRed.scaleY = 1f

        binding.colorPalette.colorYellow.scaleX = 1f
        binding.colorPalette.colorYellow.scaleY = 1f

        binding.colorPalette.colorGreen.scaleX = 1f
        binding.colorPalette.colorGreen.scaleY = 1f

        binding.colorPalette.colorBlue.scaleX = 1f
        binding.colorPalette.colorBlue.scaleY = 1f

        binding.colorPalette.colorPink.scaleX = 1f
        binding.colorPalette.colorPink.scaleY = 1f

        binding.colorPalette.colorBrown.scaleX = 1f
        binding.colorPalette.colorBrown.scaleY = 1f

        // set scale of selected view
        view.scaleX = 1.5f
        view.scaleY = 1.5f
    }
}