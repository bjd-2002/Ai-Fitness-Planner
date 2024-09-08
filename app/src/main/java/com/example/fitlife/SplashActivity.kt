package com.example.fitlife

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.fitlife.ui.theme.FitLifeTheme

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Launch the MainActivity after a 2-second delay
        Handler(mainLooper).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Remove SplashActivity from back stack
        }, 2000)
        setContent {
            FitLifeTheme {
                SplashScreen()
            }
        }
    }
}

@Composable
private fun SplashScreen() {

    val alpha = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        alpha.animateTo(1f,
            animationSpec = tween(800)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.red)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            alpha = alpha.value,
            painter = painterResource(id = R.drawable.gymicon_1),
            contentDescription = null,
            modifier = Modifier.size(250.dp)
        )
    }
}