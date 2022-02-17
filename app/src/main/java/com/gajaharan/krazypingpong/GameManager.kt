package com.gajaharan.krazypingpong

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.gajaharan.krazypingpong.sprites.Ball
import com.gajaharan.krazypingpong.sprites.Box
import com.gajaharan.krazypingpong.sprites.Sprite

class GameManager(context: Context, attributeSet: AttributeSet) :
    SurfaceView(context, attributeSet),
    SurfaceHolder.Callback {
    private var displayWidth = Resources.getSystem().displayMetrics.widthPixels
    private var displayHeight = Resources.getSystem().displayMetrics.heightPixels
    private val pointBox: Box = Box(resources, displayWidth, displayHeight)
    private val ball: Ball = Ball(resources, displayWidth, displayHeight)
    private val gameEngine = GameEngine(holder, this)

    init {
        holder.addCallback(this)
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
        pointBox.draw(canvas)
        ball.draw(canvas)
    }

    fun update() {
        if (collision(ball, pointBox)) {
            pointBox.generateNewPointBox()
        }
    }

    private fun collision(a: Sprite, b: Sprite) =
        Rect.intersects(a.getRectangle(), b.getRectangle())
}