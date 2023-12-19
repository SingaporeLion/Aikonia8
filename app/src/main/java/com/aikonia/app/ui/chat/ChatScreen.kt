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
import com.aikonia.app.ui.theme.Green
import com.aikonia.app.ui.theme.GreenShadow
import com.aikonia.app.ui.theme.RedShadow
import com.aikonia.app.ui.theme.Urbanist
import com.aikonia.app.R
import com.aikonia.app.ui.theme.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale

@Composable
fun ChatScreen(
    navigateToBack: () -> Unit,
    name: String?, // Hinzufügen des Parameters `name`
    examples: List<String>?, // Hinzufügen des Parameters `examples`
    viewModel: ChatViewModel = hiltViewModel()
) {
    val backgroundImagePainter: Painter = painterResource(id = R.drawable.background_chat10)
    val freeMessageCount by viewModel.freeMessageCount.collectAsState()
    val isProVersion by viewModel.isProVersion.collectAsState()
    val conversationId by viewModel.currentConversationState.collectAsState()
    val messagesMap by viewModel.messagesState.collectAsState()
    val isGenerating by viewModel.isGenerating.collectAsState()
    val context = LocalContext.current
    var userName by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getProVersion()
        viewModel.getFreeMessageCount()
        viewModel.getCurrentUserName { name ->
            userName = name
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

    Box(
        Modifier.fillMaxSize()
    ) {
        Image(
            painter = backgroundImagePainter,
            contentDescription = "Chat Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            Modifier.fillMaxSize()
        ) {
            AppBar(
                onClickAction = navigateToBack,
                image = R.drawable.arrow_left,
                text = if (userName.isBlank()) {
                    stringResource(R.string.app_name)
                } else {
                    "Sternenwanderer $userName"
                },
                backgroundColor = DeepBlue
            )

            Box(
                modifier = Modifier.weight(1f)
            ) {
                if (messages.isEmpty()) {
                    // Implementieren Sie hier die Capabilities und Examples Komponenten
                } else {
                    MessageList(messages = messages, modifier = Modifier.padding(bottom = paddingBottom.value))
                }
            }

            TextInput(inputText = inputText, backgroundColor = DarkViolet)
        }
    }
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
                    color = MaterialTheme.colors.onSecondary
                )
                .border(
                    2.dp,
                    color = MaterialTheme.colors.onPrimary,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(vertical = 15.dp, horizontal = 20.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.square),
                contentDescription = stringResource(R.string.app_name),
                tint = Green,
                modifier = Modifier
                    .size(width = 30.dp, height = 30.dp)


            )
            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = stringResource(id = R.string.stop_generating),
                color = MaterialTheme.colors.onSurface,
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
            Icon(
                painter = painterResource(R.drawable.app_icon),
                contentDescription = stringResource(R.string.app_name),
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier.size(width = 80.dp, height = 80.dp)
            )

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
                color = MaterialTheme.colors.onSurface,
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
                        color = MaterialTheme.colors.onSurface,
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
                                color = MaterialTheme.colors.onSecondary,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(20.dp)
                            .fillMaxWidth()

                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }


        }
    }
}

const val ConversationTestTag = "ConversationTestTag"

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
