package com.aikonia.app.common.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aikonia.app.ui.theme.Urbanist
import androidx.compose.foundation.background
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.aikonia.app.R

@Composable
fun AppBar(
    onClickAction: () -> Unit,
    image: Int,
    text: String,
    tint: Color,
    backgroundColor: Color = MaterialTheme.colors.primary, // Parameter für Hintergrundfarbe
    menuItems: (@Composable () -> Unit)? = null
) {
    val dancingScriptFontFamily = FontFamily(Font(R.font.dancingscript_medium))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = backgroundColor), // Korrekte Verwendung von backgroundColor
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            color = Color.White, // Setzt die Textfarbe auf Weiß
            style = TextStyle(
                fontWeight = FontWeight.W700,
                fontSize = 20.sp,
                fontFamily = dancingScriptFontFamily,
                textAlign = TextAlign.Center
            )
        )

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onClickAction,
                modifier = Modifier
                    .width(27.dp)
                    .height(27.dp)
            ) {
                Icon(
                    painter = painterResource(image),
                    contentDescription = "image",
                    tint = tint,
                    modifier = Modifier
                        .width(27.dp)
                        .height(27.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            if (menuItems != null) {
                menuItems()
            }
        }
    }
}