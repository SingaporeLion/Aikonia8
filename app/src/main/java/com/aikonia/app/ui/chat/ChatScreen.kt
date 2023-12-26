package com.aikonia.app.ui.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aikonia.app.common.Constants
import com.aikonia.app.common.bounceClick
import com.aikonia.app.common.components.AppBar
import com.aikonia.app.common.components.MessageCard
import com.aikonia.app.common.components.TextInput
import com.aikonia.app.common.showRewarded
import com.aikonia.app.data.model.MessageModel
import com.aikonia.app.ui.theme.Urbanist
import com.aikonia.app.R
import com.aikonia.app.ui.theme.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import android.widget.VideoView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.painterResource
import android.media.MediaPlayer
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import android.util.Log


@Composable
fun ChatScreen(
    navigateToBack: () -> Unit,
    name: String?, // Hinzufügen des Parameters `name`
    examples: List<String>?, // Hinzufügen des Parameters `examples`
    viewModel: ChatViewModel = hiltViewModel()
) {

        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current
        val mediaPlayer = remember {
            MediaPlayer.create(context, R.raw.rise_again_adobestock_356927429).apply {
                isLooping = true
                start()
            }
        }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> mediaPlayer.pause()
                Lifecycle.Event.ON_RESUME -> mediaPlayer.start()
                Lifecycle.Event.ON_DESTROY -> mediaPlayer.release()
                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            mediaPlayer.release()
        }
    }


    val isProVersion by viewModel.isProVersion.collectAsState()
    val conversationId by viewModel.currentConversationState.collectAsState()
    val messagesMap by viewModel.messagesState.collectAsState()
    val isGenerating by viewModel.isGenerating.collectAsState()

    var userName by remember { mutableStateOf("") }
    val themeColors = MaterialTheme.colors
    var videoView: VideoView? = null

    var isMuted by remember { mutableStateOf(false) }






    LaunchedEffect(Unit) {
        // Log-Ausgabe beim Laden des Bildschirms
        Log.d("ChatScreen", "ChatScreen geladen, Begrüßungsnachricht wird erstellt")

        viewModel.getProVersion()
      //  viewModel.getFreeMessageCount()
        viewModel.getCurrentUserName { name ->
            userName = name
            viewModel.getCurrentUserName { userName ->
                viewModel.createGreetingMessage() // Aufruf der Begrüßungsmethode beim Laden des ChatScreens
            }
        }
    }

    //Stumm oder Play
    if (isMuted) {
        mediaPlayer.pause()
    } else {
        mediaPlayer.start()
    }
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }
    Column {
        // IconButton mit Lautsprecher-Icon
        IconButton(onClick = { isMuted = !isMuted }) {
            Icon(
                imageVector = if (isMuted) Icons.Filled.VolumeOff else Icons.Filled.VolumeUp,
                contentDescription = if (isMuted) "Musik fortsetzen" else "Musik stummstellen"
            )
        }
    }

    val messages: List<MessageModel> =
        messagesMap[conversationId] ?: listOf()

    val paddingBottom =
        animateDpAsState(
            targetValue = if (isGenerating) 90.dp else 0.dp,
            animationSpec = tween(durationMillis = Constants.TRANSITION_ANIMATION_DURATION)
        )

    val inputText = remember { mutableStateOf("") }

    Box(Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                VideoView(context).also {
                    videoView = it
                    it.setVideoPath("android.resource://${context.packageName}/${R.raw.background_chat_animation}")
                    it.setOnPreparedListener { mediaPlayer ->
                        mediaPlayer.isLooping = true
                    }
                    it.start()
                }
            },
            modifier = Modifier.fillMaxSize() // Füllt den gesamten Bildschirm
        )

        // Der Rest des Layouts wird über dem Video platziert
        Column(Modifier.fillMaxSize()) {
            AppBar(
                onClickAction = navigateToBack,
                onMuteClick = { isMuted = !isMuted },
                isMuted = isMuted,
                image = R.drawable.arrow_left,
                text = if (userName.isBlank()) stringResource(R.string.app_name) else "Sternenwanderer $userName",
                tint = MaterialTheme.colors.onSurface,
                backgroundColor = VibrantBlue2,
                dancingScriptFontFamily = dancingScriptFontFamily // Ihre Schriftfamilie, falls verwendet
            )


            Box(modifier = Modifier.weight(1f)) {
                if (messages.isEmpty()) {
                    Capabilities(modifier = Modifier.fillMaxSize())
                } else {
                    MessageList(messages = messages, modifier = Modifier.padding(bottom = paddingBottom.value))
                }
            }

            TextInput(
                viewModel = viewModel,
                inputText = inputText
                // Hinweis: backgroundColor wird nicht mehr verwendet
            )
        }
    }
}

@Composable
fun AppBar(
    onClickAction: () -> Unit,
    onMuteClick: () -> Unit = {}, // Standardaktion hinzufügen, falls nicht verwendet
    isMuted: Boolean = false,     // Standardwert hinzufügen, falls nicht verwendet
    image: Int,
    text: String,
    tint: Color,
    backgroundColor: Color,
    dancingScriptFontFamily: FontFamily // Optional, falls verwendet
) {
    TopAppBar(
        backgroundColor = backgroundColor,
        contentColor = MaterialTheme.colors.onSurface,
        elevation = 4.dp
    ) {
        IconButton(onClick = onClickAction) {
            Icon(
                painter = painterResource(id = image),
                contentDescription = "Navigation Icon",
                tint = tint
            )
        }

        Text(
            text = text,
            style = TextStyle(
                fontFamily = dancingScriptFontFamily, // Verwenden Sie die Schriftart, falls erforderlich
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

        IconButton(onClick = onMuteClick) {
            Icon(
                imageVector = if (isMuted) Icons.Filled.VolumeOff else Icons.Filled.VolumeUp,
                contentDescription = "Stumm"
            )
        }
    }
}



@Composable
fun TextInput(
    viewModel: ChatViewModel,
    inputText: MutableState<String>
) {
    // Implementierung des TextInputs für Chat-Nachrichten
}

@Composable
fun StopButton(modifier: Modifier, onClick: () -> Unit) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 15.dp)
                .bounceClick(onClick = onClick)
                .background(
                    shape = RoundedCornerShape(16.dp),
                    color = VibrantBlue2
                )
                .border(
                    2.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(vertical = 15.dp, horizontal = 20.dp)
        ) {


            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = stringResource(id = R.string.stop_generating),
                color = White,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = Urbanist,
                    lineHeight = 25.sp
                )
            )
        }
    }
}

    @Composable
    fun Capabilities(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


        }
    }
}

    @Composable
    fun Examples(
        modifier: Modifier = Modifier,
        examples: List<String>,
        inputText: MutableState<String>
    ) {
    Box(modifier = modifier) {
        Column(
            Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = stringResource(R.string.type_something_like),
                color = White,
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = Urbanist,
                    lineHeight = 25.sp
                ),
                textAlign = TextAlign.Center
            )


            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(examples) { example ->
                    Text(
                        text = example,
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = Urbanist,
                            lineHeight = 25.sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .bounceClick(
                                onClick = {
                                    inputText.value = example
                                })
                            .background(
                                color = VibrantBlue2, // Sekundäre Variante für Abwechslung
                                shape = RoundedCornerShape(16.dp)
                            )

                            .fillMaxWidth()

                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }


        }
    }
}

val ConversationTestTag = "ConversationTestTag"

@Composable
fun MessageList(
    modifier: Modifier = Modifier,
    messages: List<MessageModel>,
) {
    val listState = rememberLazyListState()

    Box(modifier = modifier) {
        LazyColumn(
            contentPadding =
            WindowInsets.statusBars.add(WindowInsets(top = 90.dp)).asPaddingValues(),
            modifier = Modifier
                .testTag(ConversationTestTag)
                .fillMaxSize(),
            reverseLayout = true,
            state = listState,
        ) {
            items(messages.size) { index ->
                Box(modifier = Modifier.padding(bottom = if (index == 0) 10.dp else 0.dp)) {
                    Column {
                        MessageCard(
                            message = messages[index],
                            isLast = index == messages.size - 1,
                            isHuman = true
                        )
                        MessageCard(message = messages[index])
                    }
                }
            }
        }
    }

  }

