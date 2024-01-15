package com.aikonia.app.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aikonia.app.common.bounceClick

import com.aikonia.app.ui.theme.FlowerFontFamily
import com.aikonia.app.ui.theme.VibrantBlue
import com.aikonia.app.ui.theme.White

@Composable
fun ChipItem(
    text: String,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    Text(
        text = text,
        color = if (selected) White else VibrantBlue,
        style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.W600,
            fontFamily = FlowerFontFamily,
            lineHeight = 25.sp
        ), modifier = Modifier
            .padding(5.dp)
            .bounceClick(onClick = {
                onClick()
            })
            .background(
                shape = RoundedCornerShape(90.dp),
                color = if (selected) VibrantBlue else Color.Transparent
            )
            .border(2.dp, color = VibrantBlue, shape = RoundedCornerShape(90.dp))
            .padding(vertical = 10.dp, horizontal = 20.dp)
    )


}
