package com.example.myapplication.scrape

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

    var scrapeViewListener: ScrapeViewListener? = null

    private var bpBackground: Bitmap? = null
    private var bpForeground: Bitmap? = null

    private var mCanvas: Canvas? = null
    private var pathPaint: Paint? = null
    private var path: Path? = null

    private var mediaPlayer: MediaPlayer? = null

    fun initScrapeView(
        backgroundId: Int,
        foregroundId: Int,
        sound: Int,
        strokeSize: Float = 30f,
        listener: ScrapeViewListener? = null
    ) {
        scrapeViewListener = listener
        mediaPlayer = MediaPlayer.create(context, sound)

        path = Path()
        pathPaint = Paint()
        pathPaint?.apply {
            alpha = 0
            style = Paint.Style.STROKE
            strokeWidth = strokeSize
            xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
        }

        bpBackground = BitmapFactory.decodeResource(resources, backgroundId)
        bpBackground?.let {
            bpForeground = Bitmap.createBitmap(it.width, it.height, Bitmap.Config.ARGB_8888)
            bpForeground?.let { bpForeground ->
                mCanvas = Canvas(bpForeground)

                val foreground = BitmapFactory.decodeResource(resources, foregroundId)
                mCanvas?.drawBitmap(foreground, Matrix(), null)
            }
        }
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
                scrapeViewListener?.onPercentScratchOut(scratchPercent)
            }
            MotionEvent.ACTION_UP -> {
                if (mediaPlayer?.isPlaying == true) {
                    mediaPlayer?.pause()
                }
            }
        }

        path?.let { path -> pathPaint?.let { pathPaint -> mCanvas?.drawPath(path, pathPaint) } }

        invalidate()

        return true
    }

    override fun onDraw(canvas: Canvas) {
        bpBackground?.let { canvas.drawBitmap(it, 0f, 0f, null) }
        bpForeground?.let { canvas.drawBitmap(it, 0f, 0f, null) }
    }

    private fun computeScratchOutAreaSize(): Double {
        bpForeground?.let { foreground ->
            val pixels = IntArray(foreground.width * foreground.height)
            val width = foreground.width
            val height = foreground.height
            foreground.getPixels(pixels, 0, width, 0, 0, width, height)
            val sum = pixels.size
            var num = 0
            for (pixel: Int in pixels) {
                if (pixel == 0) {
                    num++
                }
            }
            return num * 100.0 / sum
        }

        return 0.0
    }
}