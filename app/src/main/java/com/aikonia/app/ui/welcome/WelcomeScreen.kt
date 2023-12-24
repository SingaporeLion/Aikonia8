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
import android.media.MediaPlayer
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.compose.material.ButtonDefaults
import androidx.compose.ui.draw.shadow
import com.aikonia.app.ui.theme.VibrantBlue2

@Composable
fun WelcomeScreen(
    navigateToChat: () -> Unit,
    playClickSound: () -> Unit

) {

    var videoView: VideoView? = null  // VideoView-Referenz hinzufügen
    val viewModel: WelcomeScreenViewModel = hiltViewModel()
    val density = LocalDensity.current.density
    var userName by remember { mutableStateOf("") }
    val customTextColor = Color(0xFF, 0xFB, 0xD8, 0xFF)
    val dancingScriptFontFamily = FontFamily(Font(R.font.dancingscript_bold))
    val alpha: Float by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        )
    )

// MediaPlayer initialisieren
    val lifecycleOwner = LocalLifecycleOwner.current
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    val appContext = LocalContext.current // Umbenennung zur Vermeidung von Konflikten


    LaunchedEffect(Unit) {
        viewModel.getCurrentUserName { name ->
            userName = name
        }
        mediaPlayer = MediaPlayer.create(appContext, R.raw.the_light_from_within_adobestock_356526076).apply {
            setOnCompletionListener {
                // Aktionen nach Beendigung der Wiedergabe
            }
        }
        mediaPlayer?.start()
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

        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> mediaPlayer?.pause()
                Lifecycle.Event.ON_RESUME -> mediaPlayer?.start()
                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            mediaPlayer?.release()
            mediaPlayer = null
        }


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
                .padding(bottom = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.Gray, shape = RoundedCornerShape(10.dp)) // Dunklerer äußerer Rand
            ) {
                Box(
                    modifier = Modifier
                        .padding(1.dp) // Geringer Abstand zum äußeren Rand
                        .background(Color.LightGray, shape = RoundedCornerShape(9.dp)) // Hellerer innerer Rand
                ) {
                    Button(
                        onClick = {
                            playClickSound()
                            navigateToChat()
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = VibrantBlue2),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .padding(1.dp) // Geringer Abstand zum inneren Rand
                    ) {
                        Text(
                            "Besuche Aikonia",
                            color = Color.White,
                            fontFamily = dancingScriptFontFamily, // Ihre benutzerdefinierte Schriftfamilie
                            fontSize = 24.sp
                        )
                    }
                }
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
    }

 
