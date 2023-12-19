package com.aikonia.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val MagischesBlau = Color(0xFF4D6D9A) // Ein tiefes, leuchtendes Blau
val LeuchtendesSmaragdgrün = Color(0xFF32CD32) // Helles, lebendiges Grün
val SanftesHimmelblau = Color(0xFF87CEEB) // Beruhigendes Blau
val MystischesViolett = Color(0xFF8A2BE2) // Sattes Violett
val LeuchtendesGelb = Color(0xFFFFD700) // Warmes, helles Gelb

private val DarkColorPalette = darkColors(
    primary = MagischesBlau,
    primaryVariant = MystischesViolett,
    secondary = LeuchtendesSmaragdgrün,
    secondaryVariant = SanftesHimmelblau,
    background = MystischesViolett,
    surface = LeuchtendesGelb,
    error = Red, // Sie können hier eine spezifische Fehlerfarbe definieren
    onPrimary = MagischesBlau,
    onSecondary = LeuchtendesSmaragdgrün,
    onSurface = SanftesHimmelblau,
    onBackground = MystischesViolett
)

private val LightColorPalette = lightColors(
    primary = SanftesHimmelblau,
    primaryVariant = LeuchtendesGelb,
    secondary = LeuchtendesSmaragdgrün,
    secondaryVariant = MagischesBlau,
    background = SanftesHimmelblau,
    surface = LeuchtendesGelb,
    error = Red, // Spezifische Fehlerfarbe
    onPrimary = SanftesHimmelblau,
    onSecondary = LeuchtendesSmaragdgrün,
    onSurface = MagischesBlau,
    onBackground = MystischesViolett
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
        typography = typography,
        shapes = Shapes,
        content = content
    )
}