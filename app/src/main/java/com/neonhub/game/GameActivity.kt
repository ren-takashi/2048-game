package com.neonhub.game

import android.os.Bundle
import androidx.activity.ComponentActivity

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Start the Native Canvas Game Engine
        val gameEngine = GameEngine(this)
        setContentView(gameEngine)
    }
}
