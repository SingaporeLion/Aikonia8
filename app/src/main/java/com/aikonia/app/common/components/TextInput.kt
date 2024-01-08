package com.aikonia.app.common.components

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aikonia.app.R
import com.aikonia.app.ui.activity.isOnline
import com.aikonia.app.ui.chat.ChatViewModel
import com.aikonia.app.ui.theme.Urbanist
import kotlinx.coroutines.launch
import java.util.Locale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.aikonia.app.ui.theme.VibrantBlue
import com.aikonia.app.ui.theme.VibrantBlue2
import com.aikonia.app.ui.theme.dancingScriptFontFamily
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors


@Composable
fun TextInput(
    viewModel: ChatViewModel = hiltViewModel(),
    inputText: MutableState<String>
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        // NoConnectionDialog Komponente (Implementierung muss vorhanden sein)
        NoConnectionDialog { showDialog = false }
    }

    val isGenerating by viewModel.isGenerating.collectAsState()


    val scope = rememberCoroutineScope()
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var hasFocus by remember { mutableStateOf(false) }
    text = text.copy(text = inputText.value)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // ... (Spracherkennungslogik)
    }

    val customTextSelectionColors = TextSelectionColors(
        handleColor = Color.White, // Setzen Sie hier Ihre gewünschte Farbe für das Handle
        backgroundColor = Color.White.copy(alpha = 0.4f) // Hintergrundfarbe der Auswahl
    )

    CompositionLocalProvider(
        LocalTextSelectionColors provides customTextSelectionColors
    ) {
        Box(
            modifier = Modifier
                .navigationBarsPadding()
                .imePadding()
                .background(MaterialTheme.colors.background),
        ) {
            Column {
                Divider(
                    color = MaterialTheme.colors.secondary, thickness = 1.dp,
                )
                Box(
                    Modifier
                        .padding(horizontal = 10.dp)
                        .padding(top = 10.dp, bottom = 10.dp)
                ) {
                    Row(Modifier.padding(all = 5.dp), verticalAlignment = Alignment.Bottom) {
                        OutlinedTextField(
                            value = text,
                            onValueChange = {
                                inputText.value = it.text
                                text = it
                            },
                            label = null,
                            placeholder = {
                                Text(
                                    stringResource(R.string.ask_me_anything),
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colors.onSurface,
                                    fontFamily = dancingScriptFontFamily,
                                    fontWeight = FontWeight.W600
                                )
                            },

                        textStyle = TextStyle(
                            color = MaterialTheme.colors.onSurface,
                            fontSize = 16.sp,
                            fontFamily = dancingScriptFontFamily,
                            fontWeight = FontWeight.W600


                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 50.dp)
                            .heightIn(max = 120.dp)
                            .padding(end = 18.dp)
                            .weight(1f)
                            .border(
                                1.dp,
                                if (hasFocus) Color.White else Color.Transparent,
                                RoundedCornerShape(16.dp)
                            )
                            .onFocusChanged { focusState -> hasFocus = focusState.hasFocus },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White,
                            focusedIndicatorColor = Color.White,
                            unfocusedIndicatorColor = Color.White,
                            disabledIndicatorColor = Color.White,
                            cursorColor = Color.White,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color.Gray,
                            backgroundColor = if (hasFocus) VibrantBlue2 else VibrantBlue2
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )

                    IconButton(
                        onClick = {
                            scope.launch {
                                if (text.text.isNotEmpty()) {

                                    if (isOnline(context).not()) {
                                        showDialog = true
                                        return@launch
                                    }

                                    // Sendet die Nachricht ohne Überprüfung der Pro-Version und des Nachrichtenlimits
                                    viewModel.sendMessage(text.text)
                                    text = TextFieldValue("")
                                    inputText.value = ""

                                } else {
                                    // Logik für Spracheingabe bleibt unverändert
                                    if (!SpeechRecognizer.isRecognitionAvailable(context)) {
                                        Toast.makeText(
                                            context,
                                            "Speech not Available",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        val intent =
                                            Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                                        intent.putExtra(
                                            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                            RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH
                                        )
                                        intent.putExtra(
                                            RecognizerIntent.EXTRA_LANGUAGE,
                                            Locale.getDefault()
                                        )
                                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Talk")
                                        launcher.launch(intent)

                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .size(50.dp)
                            .background(color = VibrantBlue2, shape = RoundedCornerShape(90.dp))
                    ) {
                        Icon(
                            if (text.text.isNotEmpty()) painterResource(R.drawable.send) else painterResource(R.drawable.voice),
                            "sendMessage",
                            modifier = Modifier.size(25.dp),
                            tint = MaterialTheme.colors.onSurface
                        )
                    }
                }
            }
        }
    }
    }
}