package com.aikonia.app.ui.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.material.MaterialTheme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Color

import com.aikonia.app.ui.theme.VibrantYellow
import com.aikonia.app.ui.theme.VibrantBlue
import com.aikonia.app.ui.theme.DeepBlue
import com.aikonia.app.ui.theme.DarkViolet

// Definieren Sie Ihre Farbpalette
val VibrantYellow = Color(0xFFFFF0BA)
val VibrantBlue = Color(0xFF2224AF)
val VibrantBlue2 = Color(0xFF155294)
val DeepBlue = Color(0xFF000033)
val DarkViolet = Color(0xFF2E005C)
val Ocean = Color(0xFF03A9F4)

val White = Color(0xFFFFFFFF)
val GreenShadow = Color(0xFF004400) // Beispiel für einen dunkleren Grünton
val TextColorDark = Color(0xFFAAAAAA) // Ein mittlerer Grauton für dunklere Bereiche
val TextColor = DeepBlue // Verwenden Sie einen dunklen Farbton für guten Kontrast auf hellem Hintergrund
val CodeBackground = DeepBlue

// Dunkle Farbpalette
private val DarkColorPalette = darkColors(
    primary = VibrantBlue2,
    primaryVariant = DarkViolet,
    secondary = VibrantYellow,
    background = DeepBlue,
    surface = DarkViolet,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,

)

// Helle Farbpalette
private val LightColorPalette = lightColors(
    primary = VibrantBlue2,
    primaryVariant = DarkViolet,
    secondary = VibrantYellow,
    background = DeepBlue,

    surface = DarkViolet,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

@Composable
fun ConversAITheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    val typography = if (darkTheme) {
        TypographyDark
    } else {
        Typography
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}