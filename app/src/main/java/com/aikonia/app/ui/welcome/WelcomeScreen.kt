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
import androidx.compose.animation.core.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.graphicsLayer
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.draw.alpha
import android.widget.VideoView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WelcomeScreen(
    navigateToChat: () -> Unit,
    playClickSound: () -> Unit
) {
    var videoView: VideoView? = null  // VideoView-Referenz hinzufügen
    val viewModel: WelcomeScreenViewModel = hiltViewModel()
    val density = LocalDensity.current.density
    var userName by remember { mutableStateOf("") }
    val customTextColor = Color(0xFE, 0xFD, 0xF5, 0xFF)
    val dancingScriptFontFamily = FontFamily(Font(R.font.dancingscript_medium))
    val alpha: Float by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        )
    )

    LaunchedEffect(Unit) {
        viewModel.getCurrentUserName { name ->
            userName = name
        }
    }
    fun smoothTransition(currentValue: Float, targetValue: Float, smoothing: Float): Float {
        return currentValue + (targetValue - currentValue) * smoothing
    }
    // Parallaxeneffekt


    val context = LocalContext.current
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    var x by remember { mutableStateOf(0f) }
    var y by remember { mutableStateOf(0f) }

    DisposableEffect(Unit) {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                x = smoothTransition(x, -event.values[0] * 1.5f, 0.1f)
                y = smoothTransition(y, -event.values[1] * 1.5f, 0.1f)
            }
            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
        }
        sensorManager.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_UI)
        onDispose { sensorManager.unregisterListener(listener) }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // VideoView als Hintergrund
        AndroidView(
            factory = { context ->
                VideoView(context).also {
                    videoView = it
                    it.setVideoPath("android.resource://${context.packageName}/${R.raw.launch_animation}")
                    it.setOnPreparedListener { mediaPlayer ->
                        mediaPlayer.isLooping = true
                        mediaPlayer.start()
                    }
                }
            },
            modifier = Modifier.matchParentSize() // Stellt sicher, dass das Video den gesamten Bildschirm ausfüllt
        )

        // Der Rest des UI-Layouts wird über dem Video platziert
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    playClickSound()
                    navigateToChat()
                },
                modifier = Modifier.padding(16.dp)

            ) {
                Text(
                    "Los geht's",
                    style = TextStyle(
                        color = Color.White, // Setzt die Textfarbe auf Weiß
                        fontSize = 18.sp, // Passen Sie die Schriftgröße nach Bedarf an
                        fontFamily = dancingScriptFontFamily // Verwenden Sie dieselbe Schriftart wie den anderen Text
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Willkommen in Aikonia, $userName",
                style = TextStyle(
                    color = Color.White, // Setzt die Textfarbe auf Weiß
                    fontSize = 24.sp,
                    fontFamily = dancingScriptFontFamily,
                    shadow = Shadow(
                        color = customTextColor.copy(alpha = 0.5f),
                        offset = androidx.compose.ui.geometry.Offset(0f, 0f),
                        blurRadius = 24.dp.value * density
                    )
                ),
                modifier = Modifier.alpha(alpha)
            )
        }
    }

    // Stellen Sie sicher, dass das Video gestoppt wird, wenn der Bildschirm nicht mehr sichtbar ist
    DisposableEffect(Unit) {
        onDispose {
            videoView?.stopPlayback()
        }
    }
    // ... (Hintergrundmusik und andere Effekte)
 }
