package com.example.myapplication.draw

import android.graphics.Color

class PaintOptions(
    var color: Int = Color.BLACK,
    var strokeWidth: Float = 8f,
    var alpha: Int = 255,
    var isEraserOn: Boolean = false
)