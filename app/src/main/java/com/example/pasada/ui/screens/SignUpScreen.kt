package com.example.pasada.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
fun SignUpScreen(
    currentStep: Int = 1,
    onStepChange: (Int) -> Unit = {},
    onNavigateBack: () -> Unit,
    onSignUpSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit = {}
) {
    var step by remember { mutableIntStateOf(currentStep) }

    LaunchedEffect(currentStep) {
        step = currentStep
    }

    val animatedAlpha = remember { Animatable(1f) }
    val animatedOffset = remember { Animatable(0f) }

    LaunchedEffect(step) {
        animatedAlpha.snapTo(0f)
        animatedOffset.snapTo(50f)
        animatedAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(300)
        )
        animatedOffset.animateTo(
            targetValue = 0f,
            animationSpec = tween(300)
        )
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var fullName by remember { mutableStateOf("") }
    var contactNumber by remember { mutableStateOf("") }
    var agreedToTerms by remember { mutableStateOf(false) }

    var isPasswordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedContent(
            targetState = step,
            transitionSpec = {
                if (targetState > initialState) {
                    (slideInHorizontally { it } + fadeIn()).togetherWith(
                        slideOutHorizontally { -it } + fadeOut()
                    )
                } else {
                    (slideInHorizontally { -it } + fadeIn()).togetherWith(
                        slideOutHorizontally { it } + fadeOut()
                    )
                }
            },
            label = "step_transition"
        ) { targetStep ->
            when (targetStep) {
                1 -> StepOneContent(
                    email = email,
                    onEmailChange = { email = it },
                    password = password,
                    onPasswordChange = { password = it },
                    confirmPassword = confirmPassword,
                    onConfirmPasswordChange = { confirmPassword = it },
                    isPasswordVisible = isPasswordVisible,
                    onTogglePasswordVisibility = { isPasswordVisible = !isPasswordVisible },
                    onContinue = {
                        step = 2
                        onStepChange(2)
                    },
                    onGoogleClick = { },
                    onNavigateToLogin = onNavigateToLogin,
                    isContinueEnabled = email.isNotEmpty() && password.isNotEmpty() && password == confirmPassword
                )
                2 -> StepTwoContent(
                    fullName = fullName,
                    onFullNameChange = { fullName = it },
                    contactNumber = contactNumber,
                    onContactNumberChange = { contactNumber = it },
                    agreedToTerms = agreedToTerms,
                    onAgreedToTermsChange = { agreedToTerms = it },
                    onBack = {
                        step = 1
                        onStepChange(1)
                    },
                    onCreateAccount = { onSignUpSuccess() },
                    isLoading = isLoading,
                    isCreateEnabled = fullName.isNotEmpty() && contactNumber.isNotEmpty() && agreedToTerms
                )
            }
        }
    }
}

@Composable
private fun StepOneContent(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    onContinue: () -> Unit,
    onGoogleClick: () -> Unit,
    onNavigateToLogin: () -> Unit,
    isContinueEnabled: Boolean
) {
    Column {
        AuthHeader(
            title = "Create Account",
            subtitle = "Yun o? Sign up ka na para makapagbook ka naman.",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(40.dp))

        AuthTextField(
            value = email,
            onValueChange = onEmailChange,
            label = "Email Address",
            placeholder = "Enter your email",
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(20.dp))

        AuthTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = "Password",
            placeholder = "Enter your password",
            trailingIcon = {
                TextButton(onClick = onTogglePasswordVisibility) {
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

        Spacer(modifier = Modifier.height(20.dp))

        AuthTextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            label = "Confirm Password",
            placeholder = "Confirm your password",
            trailingIcon = {
                TextButton(onClick = onTogglePasswordVisibility) {
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

        Spacer(modifier = Modifier.height(32.dp))

        AuthPrimaryButton(
            text = "Continue",
            onClick = onContinue,
            enabled = isContinueEnabled
        )

        Spacer(modifier = Modifier.height(24.dp))

        AuthDivider(text = "Or continue with")

        Spacer(modifier = Modifier.height(24.dp))

        AuthSocialButton(
            text = "Continue with Google",
            onClick = onGoogleClick,
            icon = {
                Icon(
                    painter = painterResource(id = com.example.pasada.R.drawable.google_icon),
                    contentDescription = "Google",
                    tint = Color.Unspecified
                )
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = buildAnnotatedString {
                append("Already have an account? ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = PasadaPrimary)) {
                    append("Sign In")
                }
            },
            color = Color.White,
            fontSize = 14.sp,
            fontFamily = ReadexProFontFamily,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onNavigateToLogin() }
        )
    }
}

@Composable
private fun StepTwoContent(
    fullName: String,
    onFullNameChange: (String) -> Unit,
    contactNumber: String,
    onContactNumberChange: (String) -> Unit,
    agreedToTerms: Boolean,
    onAgreedToTermsChange: (Boolean) -> Unit,
    onBack: () -> Unit,
    onCreateAccount: () -> Unit,
    isLoading: Boolean,
    isCreateEnabled: Boolean
) {
    Column {
        AuthHeader(
            title = "Almost There!",
            subtitle = "Kaunti na lang, bossing. Hehehe.",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(40.dp))

        AuthTextField(
            value = fullName,
            onValueChange = onFullNameChange,
            label = "Full Name",
            placeholder = "Enter your full name",
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(20.dp))

        AuthTextField(
            value = contactNumber,
            onValueChange = onContactNumberChange,
            label = "Contact Number",
            placeholder = "9123456789",
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = agreedToTerms,
                onCheckedChange = onAgreedToTermsChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = PasadaPrimary,
                    uncheckedColor = Color.White.copy(alpha = 0.5f),
                    checkmarkColor = Color.White
                )
            )
            Text(
                text = buildAnnotatedString {
                    append("I agree to Pasada's ")
                    withStyle(style = SpanStyle(color = PasadaPrimary, fontWeight = FontWeight.SemiBold)) {
                        append("Terms and Conditions")
                    }
                    append(" and ")
                    withStyle(style = SpanStyle(color = PasadaPrimary, fontWeight = FontWeight.SemiBold)) {
                        append("Privacy Policy")
                    }
                },
                color = Color.White,
                fontSize = 12.sp,
                fontFamily = ReadexProFontFamily,
                modifier = Modifier.clickable { onAgreedToTermsChange(!agreedToTerms) }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        AuthPrimaryButton(
            text = "Create Account",
            onClick = onCreateAccount,
            isLoading = isLoading,
            enabled = isCreateEnabled
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Back",
                color = PasadaTextDim,
                fontFamily = ReadexProFontFamily,
                textAlign = TextAlign.Center
            )
        }
    }
}
