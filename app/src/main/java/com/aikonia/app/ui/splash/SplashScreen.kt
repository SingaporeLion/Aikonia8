package com.aikonia.app.ui.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.aikonia.app.R
import android.content.SharedPreferences
import com.aikonia.app.data.source.local.UserRepository

@Composable
fun SplashScreen(
    navigateToStartChat: () -> Unit,
    navigateToWelcome: () -> Unit,
    userRepository: UserRepository
) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnimation = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 4000)
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(2000)
        // Prüfen, ob Benutzerdaten vorhanden sind
        val userName = userRepository.getCurrentUserName()
        if (userName.isEmpty() || userName == "Unbekannter Benutzer") {
            // Keine Benutzerdaten vorhanden, navigiere zu StartChat
            navigateToStartChat()
        } else {
            // Benutzerdaten vorhanden, navigiere zu Welcome
            navigateToWelcome()
        }
    }

    SplashDesign(alpha = alphaAnimation.value)
}


@Composable
fun SplashDesign(alpha: Float) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .size(260.dp)
                .alpha(alpha = alpha),
            painter = painterResource(id = R.drawable.app_icon),
            contentDescription = "App Icon"
        )
    }
}

