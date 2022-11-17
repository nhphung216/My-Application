package com.example.myapplication.draw

import android.content.Context
import android.graphics.*
import android.media.MediaPlayer
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.myapplication.R

class ScrapeView : View {

    var bpBackground: Bitmap? = null
    var bpForeground: Bitmap? = null
    var mCanvas: Canvas? = null
    var pathPaint: Paint? = null
    var path: Path? = null

    private var contentPaint: Paint? = null
    private var mediaPlayer: MediaPlayer? = null

    private val content = "BigRich"

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context?) : super(context) {
        init(context)
    }

    private fun init(context: Context?) {
        mediaPlayer = MediaPlayer.create(context, R.raw.scratcheffect)

        pathPaint = Paint()
        pathPaint?.apply {
            alpha = 0
            style = Paint.Style.STROKE
            strokeWidth = 50f
            xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
        }

        path = Path()
        bpBackground = BitmapFactory.decodeResource(resources, R.drawable.s)
        bpForeground = Bitmap.createBitmap(
            bpBackground!!.width, bpBackground!!.height, Bitmap.Config.ARGB_8888
        )

        mCanvas = Canvas(bpForeground!!)
        contentPaint = Paint()
        contentPaint?.color = Color.WHITE
        contentPaint?.textSize = 100f
        contentPaint?.strokeWidth = 20f
        mCanvas?.drawColor(Color.GRAY)
        mCanvas?.drawText(
            content,
            (mCanvas!!.width / 4).toFloat(),
            (mCanvas!!.height / 2).toFloat(),
            contentPaint!!
        )
    }

    var lastEvent: MotionEvent? = null

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.e("ScrapeView", "--------------------------------")
        Log.e("ScrapeView", "event      : ${event.x}, ${event.y}")
        Log.e("ScrapeView", "last event : ${lastEvent?.x}, ${lastEvent?.y}")

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
            }
            MotionEvent.ACTION_UP -> {
                if (mediaPlayer?.isPlaying == true) {
                    mediaPlayer?.pause()
                }
                lastEvent = event
            }
        }

        mCanvas?.drawPath(path!!, pathPaint!!)
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(bpBackground!!, 0f, 0f, null)
        canvas.drawBitmap(bpForeground!!, 0f, 0f, null)
    }
}