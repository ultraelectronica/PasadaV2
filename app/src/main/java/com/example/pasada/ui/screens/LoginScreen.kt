package com.example.pasada.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pasada.ui.components.*
import com.example.pasada.ui.theme.PasadaPrimary
import com.example.pasada.ui.theme.PasadaTextDim
import com.example.pasada.ui.theme.ReadexProFontFamily

@Composable
fun LoginScreen(
    onNavigateBack: () -> Unit,
    onLoginSuccess: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onNavigateToSignUp: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthHeader(
            title = "Welcome Back",
            subtitle = "Sign in na para makapagbook ka ulit.",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(40.dp))

        AuthTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email Address",
            placeholder = "Enter your email",
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(20.dp))

        AuthTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            placeholder = "Enter your password",
            trailingIcon = {
                TextButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Text(
                        text = if (isPasswordVisible) "Hide" else "Show",
                        color = PasadaTextDim,
                        fontSize = 12.sp,
                        fontFamily = ReadexProFontFamily
                    )
                }
            },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = PasadaPrimary,
                        checkmarkColor = Color.White,
                        uncheckedColor = Color.White.copy(alpha = 0.5f)
                    )
                )
                Text(
                    text = "Remember me",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontFamily = ReadexProFontFamily,
                    modifier = Modifier.clickable { rememberMe = !rememberMe }
                )
            }

            Text(
                text = "Forgot Password?",
                color = PasadaPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = ReadexProFontFamily,
                modifier = Modifier.clickable { onNavigateToForgotPassword() }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        AuthPrimaryButton(
            text = "Log In",
            onClick = { onLoginSuccess() },
            isLoading = isLoading
        )

        Spacer(modifier = Modifier.height(24.dp))

        AuthDivider(text = "Or continue with")

        Spacer(modifier = Modifier.height(24.dp))

        AuthSocialButton(
            text = "Continue with Google",
            onClick = { /* Handle Google Sign In */ },
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Google",
                    tint = Color.White
                )
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = buildAnnotatedString {
                append("Don't have an account? ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = PasadaPrimary)) {
                    append("Sign up")
                }
            },
            color = Color.White,
            fontSize = 14.sp,
            fontFamily = ReadexProFontFamily,
            modifier = Modifier.clickable { onNavigateToSignUp() }
        )
    }
}
