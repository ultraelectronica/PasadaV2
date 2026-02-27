package com.example.pasada.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font
import com.example.pasada.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val readexProName = GoogleFont("Readex Pro")

val ReadexProFontFamily = FontFamily(
    Font(googleFont = readexProName, fontProvider = provider, weight = FontWeight.Normal),
    Font(googleFont = readexProName, fontProvider = provider, weight = FontWeight.Medium),
    Font(googleFont = readexProName, fontProvider = provider, weight = FontWeight.SemiBold),
    Font(googleFont = readexProName, fontProvider = provider, weight = FontWeight.Bold),
    Font(googleFont = readexProName, fontProvider = provider, weight = FontWeight.ExtraBold)
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