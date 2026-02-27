package com.example.pasada.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import com.example.pasada.R

val ReadexProFontFamily = FontFamily(
    Font(R.font.readex_pro_extralight, FontWeight.ExtraLight),
    Font(R.font.readex_pro_light, FontWeight.Light),
    Font(R.font.readex_pro_regular, FontWeight.Normal),
    Font(R.font.readex_pro_medium, FontWeight.Medium),
    Font(R.font.readex_pro_semibold, FontWeight.SemiBold),
    Font(R.font.readex_pro_bold, FontWeight.Bold)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = ReadexProFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    displayLarge = TextStyle(
        fontFamily = ReadexProFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 56.sp,
        lineHeight = 61.6.sp,
        letterSpacing = (-1.0).sp
    ),
    bodyMedium = TextStyle(
        fontFamily = ReadexProFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        letterSpacing = 0.2.sp
    ),
    labelLarge = TextStyle(
        fontFamily = ReadexProFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    )
)