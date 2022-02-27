package com.gajaharan.krazypingpong

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
import android.os.Bundle
import android.view.Window.FEATURE_NO_TITLE
import android.widget.Button

class GameMenuActivity : Activity() {

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(FEATURE_NO_TITLE)
        setContentView(R.layout.activity_game_menu)

        val startGameButton: Button = findViewById(R.id.startGameButton)

        startGameButton.setOnClickListener {
            val intent = Intent(applicationContext, GameActivity::class.java)
            intent.flags =FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivityIfNeeded(intent, 1)
        }

    }
}