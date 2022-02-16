package com.gajaharan.krazypingpong

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.util.*

class GameManager(context: Context, attributeSet: AttributeSet) :
    SurfaceView(context, attributeSet),
    SurfaceHolder.Callback {
    private var displayWidth = Resources.getSystem().displayMetrics.widthPixels
    private var displayHeight = Resources.getSystem().displayMetrics.heightPixels
    private var boxPoint: Point
    private val gameEngine = GameEngine(holder, this)

    init {
        holder.addCallback(this)
        boxPoint = Point(Random().nextInt(displayWidth), Random().nextInt(displayHeight))
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        gameEngine.setRunning(true)
        gameEngine.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        gameEngine.surfaceHolder = holder
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        try {
            gameEngine.setRunning(false)
            gameEngine.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.drawColor(Color.BLACK)

        val boxPaint = Paint()
        val boxSizeX = 40
        val boxSizeY = 40
        boxPaint.setARGB(
            255,
            Random().nextInt(256),
            Random().nextInt(256),
            Random().nextInt(256)
        )

        canvas?.drawRect(
            Rect(boxPoint.x, boxPoint.y, boxPoint.x + boxSizeX, boxPoint.y + boxSizeY),
            boxPaint
        )
    }
}