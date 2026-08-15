package com.neonhub.game

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    private lateinit var dataManager: DataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataManager = DataManager(this)

        setContent {
            NeonHubMenu(dataManager) {
                // Navigate to Game
                startActivity(Intent(this, GameActivity::class.java))
            }
        }
    }
}

@Composable
fun NeonHubMenu(dataManager: DataManager, onPlayClick: () -> Unit) {
    val neonCyan = Color(0xFF00FFFF)
    val neonGold = Color(0xFFFFD700)
    val bgColor = Color(0xFF050510)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("NEON HUB", color = neonCyan, fontSize = 24.sp, fontWeight = FontWeight.Black)
            Row {
                Text("💰 ${dataManager.coins}", color = neonGold, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(10.dp))
                Text("💎 ${dataManager.diamonds}", color = neonCyan, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Play Button
        Button(
            onClick = onPlayClick,
            colors = ButtonDefaults.buttonColors(containerColor = neonCyan),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(60.dp)
        ) {
            Text("PLAY MISSION ${dataManager.maxLevel}", color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Black)
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // Terminal Button
        Button(
            onClick = { /* Open Native Terminal UI here later */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF111111)),
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("TERMINAL SHOP", color = Color.White)
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}
