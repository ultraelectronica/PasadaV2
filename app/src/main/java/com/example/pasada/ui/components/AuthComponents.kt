package com.example.pasada.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pasada.ui.theme.*

@Composable
fun AuthBackground(content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PasadaDarkBg),
        content = content
    )
}

@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontFamily = ReadexProFontFamily,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = placeholder, color = PasadaTextDim, fontFamily = ReadexProFontFamily) },
            trailingIcon = trailingIcon,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PasadaPrimary,
                unfocusedBorderColor = PasadaBorderWhite,
                focusedContainerColor = PasadaTransparentWhite,
                unfocusedContainerColor = PasadaTransparentWhite,
                cursorColor = PasadaPrimary,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun AuthPrimaryButton(
    text: String,
    onClick: () -> Unit,
    icon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = PasadaPrimary,
            contentColor = Color.White,
            disabledContainerColor = PasadaPrimary.copy(alpha = 0.5f),
            disabledContentColor = Color.White.copy(alpha = 0.5f)
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(14.dp),
        enabled = enabled && !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 2.dp,
                modifier = Modifier.size(24.dp)
            )
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                icon?.invoke()
                if (icon != null) Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = text,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = ReadexProFontFamily
                )
            }
        }
    }
}

@Composable
fun AuthSocialButton(
    text: String,
    onClick: () -> Unit,
    icon: @Composable (() -> Unit),
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = PasadaTransparentWhite,
            contentColor = Color.White
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(
                width = 1.5.dp,
                color = PasadaBorderWhite,
                shape = RoundedCornerShape(14.dp)
            ),
        shape = RoundedCornerShape(14.dp),
        enabled = !isLoading,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 2.dp,
                modifier = Modifier.size(24.dp)
            )
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                icon()
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = text,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = ReadexProFontFamily
                )
            }
        }
    }
}

@Composable
fun AuthDivider(text: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f), color = PasadaSurfaceBg)
        Text(
            text = text,
            color = PasadaTextDim,
            fontSize = 12.sp,
            fontFamily = ReadexProFontFamily,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        HorizontalDivider(modifier = Modifier.weight(1f), color = PasadaSurfaceBg)
    }
}

@Composable
fun AuthHeader(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White,
            fontFamily = ReadexProFontFamily,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = subtitle,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Gray,
            fontFamily = ReadexProFontFamily,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}
