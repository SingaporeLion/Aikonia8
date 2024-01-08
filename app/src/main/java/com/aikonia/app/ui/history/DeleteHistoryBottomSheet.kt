package com.aikonia.app.ui.history

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aikonia.app.common.bounceClick

import com.aikonia.app.ui.theme.GreenShadow
import com.aikonia.app.ui.theme.Urbanist
import com.aikonia.app.ui.theme.White
import com.aikonia.app.R
import com.aikonia.app.ui.theme.VibrantBlue
import com.aikonia.app.ui.theme.VibrantBlue2
import com.aikonia.app.ui.theme.dancingScriptFontFamily

@Composable
fun DeleteHistoryBottomSheet(
    onCancelClick: () -> Unit = {},
    onConfirmClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .border(1.dp, MaterialTheme.colors.onPrimary, RoundedCornerShape(35.dp))
            .padding(16.dp)
            .padding(bottom = 26.dp), // Erhöhen Sie den unteren Padding-Wert
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(50.dp)
                .height(3.dp)
                .background(MaterialTheme.colors.onPrimary, RoundedCornerShape(80.dp))
        )

        Text(
            text = stringResource(R.string.clear_all_history),
            color = Color.White,
            style = TextStyle(
                fontSize = 26.sp,
                fontWeight = FontWeight.W700,
                fontFamily = dancingScriptFontFamily,
                lineHeight = 25.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 20.dp)
        )

        Divider(
            color = Color.White,
            thickness = 1.dp,
            modifier = Modifier.padding(10.dp)
        )


        Text(
            text = stringResource(R.string.are_you_sure_delete_all_history),
            color = Color.White,
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.W700,
                fontFamily = dancingScriptFontFamily,
                lineHeight = 25.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 20.dp)
        )

        Row(modifier = Modifier.padding(vertical = 20.dp)) {
            Card(
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
                    .bounceClick {
                        onCancelClick()
                    },
                elevation = 0.dp,
                backgroundColor = VibrantBlue2,
                shape = RoundedCornerShape(80.dp),
            ) {
                Row(
                    Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.cancel),
                        color = White,
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.W700,
                            fontFamily = dancingScriptFontFamily
                        ),
                        textAlign = TextAlign.Center
                    )

                }
            }

            Spacer(modifier = Modifier.width(20.dp))
            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .bounceClick {
                        onConfirmClick()
                    },
                elevation = 5.dp,
                backgroundColor = Color.White, // Weißer Hintergrund
                shape = RoundedCornerShape(80.dp),
                border = BorderStroke(1.dp, VibrantBlue2) // Rand in VibrantBlue2
            ) {
                Row(
                    Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.yes_clear_all),
                        color = VibrantBlue2, // Text in VibrantBlue2
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.W700,
                            fontFamily = dancingScriptFontFamily
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}