package com.aikonia.app.ui.welcome
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp
import com.aikonia.app.R // Ersetzen Sie dies durch Ihren tatsächlichen Ressourcen-Importpfad


@Composable
fun WelcomeScreen(
    userName: String,
    navigateToHistory: () -> Unit,
    playClickSound: () -> Unit
) {
    val backgroundImage = painterResource(id = R.drawable.aikonia_screen) // Ersetzen Sie mit Ihrem Hintergrundbild
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = backgroundImage,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Animierter Bewegungseffekt auf das Hintergrundbild
        // ...

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Schön Dich wiederzusehen, $userName!",
                style = TextStyle(color = Color.White, fontSize = 24.sp)
                // Weitere Stiloptionen...
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    playClickSound()
                    navigateToHistory()
                }
            ) {
                Text("Zum Menü")
            }
        }
    }

    // Hintergrundmusik abspielen
    // ...
}