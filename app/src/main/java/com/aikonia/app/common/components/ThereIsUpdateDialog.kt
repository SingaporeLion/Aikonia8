package com.aikonia.app.common.components

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.aikonia.app.common.bounceClick
import com.aikonia.app.ui.theme.FlowerFontFamily
import com.aikonia.app.ui.theme.White
import com.aikonia.app.R
import com.aikonia.app.ui.theme.VibrantBlue


@Composable
fun ThereIsUpdateDialog(
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {

        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background, RoundedCornerShape(35.dp))
                .border(1.dp, MaterialTheme.colors.onPrimary, RoundedCornerShape(35.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.update),
                color = MaterialTheme.colors.surface,
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = FlowerFontFamily,
                    lineHeight = 25.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 20.dp)
            )

            Divider(
                color = MaterialTheme.colors.secondary,
                thickness = 1.dp,
                modifier = Modifier.padding(10.dp)
            )


            Text(
                text = stringResource(R.string.there_is_new_version),
                color = MaterialTheme.colors.surface,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = FlowerFontFamily,
                    lineHeight = 25.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 20.dp)
            )

            Row(modifier = Modifier.padding(vertical = 20.dp)) {
                Card(
                    modifier = Modifier
                        .height(60.dp)
                        .weight(1f)
                        .bounceClick {
                            onDismiss()
                        },
                    elevation = 0.dp,
                    backgroundColor = VibrantBlue,
                    shape = RoundedCornerShape(90.dp),
                ) {
                    Row(
                        Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.update),
                            color = White,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W700,
                                fontFamily = FlowerFontFamily
                            ),
                            textAlign = TextAlign.Center
                        )

                    }
                }

            }
        }
    }
}

