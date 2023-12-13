package com.aikonia.app.ui.welcome
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
import com.aikonia.app.data.source.local.UserRepository
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun WelcomeScreen(
    navigateToChat: () -> Unit,
    playClickSound: () -> Unit
) {
    val userRepository: UserRepository by hiltViewModel()
    val backgroundImage = painterResource(id = R.drawable.aikonia_screen)

    // Initialisieren des Benutzernamens als leerer String
    var userName by remember { mutableStateOf("") }

    // LaunchedEffect, um den Benutzernamen bei jedem Rendering der Komponente zu überprüfen
    LaunchedEffect(Unit) {
        userName = userRepository.getCurrentUserName()
        if (userName != "Unbekannter Benutzer") {
            navigateToChat()
        }
    }

    // UI für den Willkommensbildschirm
    if (userName.isEmpty() || userName == "Unbekannter Benutzer") {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = backgroundImage,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Schön Dich wiederzusehen, $userName!",
                    style = TextStyle(color = Color.White, fontSize = 24.sp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        playClickSound()
                        navigateToChat()
                    }
                ) {
                    Text("Zum Menü")
                }
            }
        }
    }
    // Hintergrundmusik abspielen
    // ...
}