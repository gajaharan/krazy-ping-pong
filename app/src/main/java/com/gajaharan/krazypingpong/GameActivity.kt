package com.gajaharan.krazypingpong

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
import android.os.Bundle
import android.view.View
import android.view.Window.FEATURE_NO_TITLE
import kotlinx.android.synthetic.main.activity_main.*

class GameActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main)
    }

    fun onPause(view: View) {
        gameManager.resumeGame(false)
        val intent = Intent(applicationContext, GameMenuActivity::class.java)
        intent.flags = FLAG_ACTIVITY_REORDER_TO_FRONT
        startActivityIfNeeded(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            gameManager.resumeGame(true)
        }
    }

    fun onGameStart(view: View) {
        gameStart.visibility = View.GONE
        gameManager.initGame()
    }
}
