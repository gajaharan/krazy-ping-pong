package com.gajaharan.krazypingpong

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.media.MediaPlayer
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_MOVE
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.gajaharan.krazypingpong.sprites.*
import java.util.*

class GameManager(context: Context, attributeSet: AttributeSet) :
    SurfaceView(context, attributeSet),
    SurfaceHolder.Callback {
    private var displayWidth = Resources.getSystem().displayMetrics.widthPixels
    private var displayHeight = Resources.getSystem().displayMetrics.heightPixels
    private val pointBox: Box = Box(resources, displayWidth, displayHeight)
    private val ball: Ball = Ball(resources, displayWidth, displayHeight)
    private val leftPaddle: LeftPaddle = LeftPaddle(resources, displayWidth, displayHeight)
    private val rightPaddle: RightPaddle = RightPaddle(resources, displayWidth, displayHeight)
    private val gameEngine = GameEngine(holder, this)
    private var sharedPreferences: SharedPreferences
    private val mpHit = MediaPlayer.create(context, R.raw.hit)
    private val mpMiss = MediaPlayer.create(context, R.raw.miss)

    init {
        holder.addCallback(this)
        sharedPreferences = context.getSharedPreferences("my_pref", 0)
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
        //drawRegenBox(canvas)

        pointBox.draw(canvas)
        ball.draw(canvas)
        leftPaddle.draw(canvas)
        rightPaddle.draw(canvas)

        drawCourt(canvas)

    }

    private fun drawCourt(canvas: Canvas?) {
        val courtPain = Paint()
        courtPain.color = Color.WHITE
        courtPain.strokeWidth = 10f

        val borderLeft = 0f
        val borderRight = displayWidth.toFloat()
        val borderTop = 0f
        val borderBottom = displayHeight.toFloat()

        canvas?.drawLine(borderLeft, borderTop, borderLeft, borderBottom, courtPain)
        canvas?.drawLine(borderLeft, borderTop, borderRight, borderTop, courtPain)
        canvas?.drawLine(borderLeft, borderBottom, borderRight, borderBottom, courtPain)
        canvas?.drawLine(borderRight, borderTop, borderRight, borderBottom, courtPain)

//        for (a in displayHeight/10 downTo 1 step 2) {
//            canvas?.drawLine(displayWidth/2.toFloat(), (displayHeight - a).toFloat(), displayWidth/2.toFloat(), a.toFloat(), courtPain)
//        }

        canvas?.drawLine(displayWidth/2.toFloat(), 0f, displayWidth/2.toFloat(), displayHeight.toFloat(), courtPain)
    }

    private fun drawRegenBox(canvas: Canvas?) {
        val boardLeft = displayWidth * 0.05f
        val boardRight = displayWidth * 0.95f
        val boardTop = displayHeight * 0.1f
        val boardBottom = displayHeight * 0.9f

        val boardPaint = Paint()
        boardPaint.color = Color.RED

        // top left corner, bottom left corner
        canvas?.drawLine(boardLeft, boardTop, boardLeft, boardBottom, boardPaint)
        //  top left corner, top right corner
        canvas?.drawLine(boardLeft, boardTop, boardRight, boardTop, boardPaint)
        //  bottom left corner, bottom right corner
        canvas?.drawLine(boardLeft, boardBottom, boardRight, boardBottom, boardPaint)
        // top right corner, bottom right corner
        canvas?.drawLine(boardRight, boardTop, boardRight, boardBottom, boardPaint)
    }


    fun update() {
        val randomVelocity = intArrayOf(-35, -30, -25, 25, 30, 35)[Random().nextInt(6)]

        //leftPaddle.movePaddle(ball)
        rightPaddle.movePaddle(ball)

        if (collision(ball, pointBox)) {
            pointBox.generateNewPointBox()
        }

        if (ball.x < leftPaddle.x) {
            ball.x = 1 + Random().nextInt(displayWidth - ball.width - 1)
            ball.y = 0

            ball.setVelocity(randomVelocity, 32)
            rightPaddle.score++
            mpMiss?.start()
        }

        if (ball.x > rightPaddle.x) {
            ball.x = 1 + Random().nextInt(displayWidth - ball.width - 1)
            ball.y = 0

            ball.setVelocity(randomVelocity, 32)
            leftPaddle.score++
            mpMiss?.start()
        }

        if (collision(ball, leftPaddle)) {
            ball.setVelocityX()
            ball.setVelocityY()
            mpHit?.start()
        }

        if (collision(ball, rightPaddle)) {
            ball.setVelocityX()
            ball.setVelocityY()
            mpHit?.start()
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