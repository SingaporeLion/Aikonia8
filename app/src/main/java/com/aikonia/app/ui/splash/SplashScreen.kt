package com.aikonia.app.ui.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.aikonia.app.R

@Composable
fun SplashScreen(
    navigateToStartChat: () -> Unit
) {

    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnimation = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 4000
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(2000)
        navigateToStartChat()
    }

    SplashDesign(alpha = alphaAnimation.value)
}

@Composable
fun SplashDesign(alpha: Float) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .size(260.dp)
                .alpha(alpha = alpha),
            painter = painterResource(id = R.drawable.app_icon),
            contentDescription = stringResource(R.string.app_icon),
        )
    }
}

