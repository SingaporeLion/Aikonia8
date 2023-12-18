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
import com.aikonia.app.data.source.local.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.aikonia.app.ui.startchat.StartChatViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import android.util.Log

@Composable
fun SplashScreen(
    navigateToStartChat: () -> Unit,
    navigateToWelcome: () -> Unit,
    startChatViewModel: StartChatViewModel = hiltViewModel()
) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnimation = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 4000)
    )

    // Beobachtung des Benutzerstatus aus dem ViewModel
    val isUserDataSaved by startChatViewModel.isUserDataSaved.collectAsState()

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(2000)
        // Entscheiden, ob zu StartChat oder Welcome navigiert wird

            Log.d("SplashScreen", "Checking user data saved status")
            if (isUserDataSaved) {
                Log.d("SplashScreen", "User data is saved, navigating to WelcomeScreen")
                navigateToWelcome()
            } else {
                Log.d("SplashScreen", "No user data, navigating to StartChatScreen")
                navigateToStartChat()
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

