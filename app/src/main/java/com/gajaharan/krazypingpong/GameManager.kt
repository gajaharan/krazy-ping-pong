package com.gajaharan.krazypingpong

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_MOVE
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.gajaharan.krazypingpong.sprites.Ball
import com.gajaharan.krazypingpong.sprites.Box
import com.gajaharan.krazypingpong.sprites.LeftPaddle
import com.gajaharan.krazypingpong.sprites.Sprite

class GameManager(context: Context, attributeSet: AttributeSet) :
    SurfaceView(context, attributeSet),
    SurfaceHolder.Callback {
    private var displayWidth = Resources.getSystem().displayMetrics.widthPixels
    private var displayHeight = Resources.getSystem().displayMetrics.heightPixels
    private val pointBox: Box = Box(resources, displayWidth, displayHeight)
    private val ball: Ball = Ball(resources, displayWidth, displayHeight)
    private val leftPaddle: LeftPaddle = LeftPaddle(resources, displayWidth, displayHeight)
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
        leftPaddle.draw(canvas)
    }

    fun update() {
        if (collision(ball, pointBox)) {
            pointBox.generateNewPointBox()
        }
    }

    private fun collision(a: Sprite, b: Sprite) =
        Rect.intersects(a.getRectangle(), b.getRectangle())

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x
        val touchY = event.y
        var oldEventY = 0f
        var oLeftPaddleY = 0f

        if (touchX >= leftPaddle.x) {
            val action = event.action;
            if (action == ACTION_DOWN) {
                oldEventY = event.y;
                oLeftPaddleY = leftPaddle.y.toFloat()
            }
            if (action == ACTION_MOVE) {
                val shift = oldEventY - touchY
                val newPaddleY = oLeftPaddleY - shift
                when {
                    // paddle reach top screen
                    (newPaddleY <= 0) -> leftPaddle.y = 0
                    // paddle reach bottom screen
                    (newPaddleY >= displayHeight - leftPaddle.height) -> leftPaddle.y =
                        displayHeight - leftPaddle.height
                    // otherwise move paddle up or down
                    else -> leftPaddle.y = newPaddleY.toInt()
                }
            }
        }
        return true
    }
}