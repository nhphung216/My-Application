package com.example.myapplication.draw

import android.content.Context
import android.graphics.*
import android.media.MediaPlayer
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class ScrapeTicketView : View {

    companion object {
        private const val TAG: String = "ScrapeTicketView"
    }

    interface ScrapeViewListener {
        fun onPercentScratchOut(percent: Double)
    }

    var listener: ScrapeViewListener? = null

    private var bpBackground: Bitmap? = null
    private var bpForeground: Bitmap? = null

    private var mCanvas: Canvas? = null
    private var pathPaint: Paint? = null
    private var path: Path? = null

    private var mediaPlayer: MediaPlayer? = null

    fun initScrapeView(resIdBackground: Int, resIdForeground: Int, scratchEffect: Int) {
        mediaPlayer = MediaPlayer.create(context, scratchEffect)

        path = Path()
        pathPaint = Paint()
        pathPaint?.apply {
            alpha = 0
            style = Paint.Style.STROKE
            strokeWidth = 30f
            xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
        }

        bpBackground = BitmapFactory.decodeResource(resources, resIdBackground)
        bpForeground = Bitmap.createBitmap(
            bpBackground!!.width, bpBackground!!.height, Bitmap.Config.ARGB_8888
        )
        mCanvas = Canvas(bpForeground!!)

        val foreground = BitmapFactory.decodeResource(resources, resIdForeground)
        mCanvas?.drawBitmap(foreground, Matrix(), null)
    }

    constructor(context: Context?) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path?.reset()
                path?.moveTo(event.x, event.y)
            }
            MotionEvent.ACTION_MOVE -> {
                path?.lineTo(event.x, event.y)
                if (mediaPlayer?.isPlaying == true) {
                    mediaPlayer?.pause()
                }
                mediaPlayer?.start()

                val scratchPercent = computeScratchOutAreaSize()
                Log.e(TAG, "scratchPercent: ${scratchPercent}%")
                listener?.onPercentScratchOut(scratchPercent)
            }
            MotionEvent.ACTION_UP -> {
                if (mediaPlayer?.isPlaying == true) {
                    mediaPlayer?.pause()
                }
            }
        }

        mCanvas?.drawPath(path!!, pathPaint!!)
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas) {
        bpBackground?.let { canvas.drawBitmap(it, 0f, 0f, null) }
        bpForeground?.let { canvas.drawBitmap(it, 0f, 0f, null) }
    }

    private fun computeScratchOutAreaSize(): Double {
        if (bpForeground == null) return 0.0
        val pixels = IntArray(bpForeground!!.width * bpForeground!!.height)
        val width = bpForeground!!.width
        val height = bpForeground!!.height
        bpForeground!!.getPixels(pixels, 0, width, 0, 0, width, height)
        val sum = pixels.size
        var num = 0
        for (pixel: Int in pixels) {
            if (pixel == 0) {
                num++
            }
        }
        return num * 100.0 / sum
    }
}