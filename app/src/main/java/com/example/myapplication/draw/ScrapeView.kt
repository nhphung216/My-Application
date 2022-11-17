package com.example.myapplication.draw

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import com.example.myapplication.R
import android.view.MotionEvent
import android.view.View

class ScrapeView : View {

    lateinit var bpBackground: Bitmap
    lateinit var bpForeground: Bitmap
    lateinit var mCanvas: Canvas
    lateinit var pathPaint: Paint
    lateinit var path: Path
    lateinit var contentPaint: Paint
    private val content = "BigRich"

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?) : super(context) {
        init()
    }

    private fun init() {
        pathPaint = Paint()
        pathPaint.alpha = 0
        pathPaint.style = Paint.Style.STROKE
        pathPaint.strokeWidth = 50f
        pathPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        pathPaint.strokeJoin = Paint.Join.ROUND
        pathPaint.strokeCap = Paint.Cap.ROUND

        path = Path()
        bpBackground = BitmapFactory.decodeResource(resources, R.drawable.s)
        bpForeground = Bitmap.createBitmap(
            bpBackground.width,
            bpBackground.height,
            Bitmap.Config.ARGB_8888
        )

        mCanvas = Canvas(bpForeground)
        contentPaint = Paint()
        contentPaint.color = Color.WHITE
        contentPaint.textSize = 100f
        contentPaint.strokeWidth = 20f
        mCanvas.drawColor(Color.GRAY)
        mCanvas.drawText(
            content,
            (mCanvas.width / 4).toFloat(),
            (mCanvas.height / 2).toFloat(),
            contentPaint
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.reset()
                path.moveTo(event.x, event.y)
            }
            MotionEvent.ACTION_MOVE -> path.lineTo(event.x, event.y)
        }
        mCanvas.drawPath(path, pathPaint)
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(bpBackground, 0f, 0f, null)
        canvas.drawBitmap(bpForeground, 0f, 0f, null)
    }
}