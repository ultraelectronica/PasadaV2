package com.example.pasada.ui.screens

import android.app.Activity
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.lottiefiles.dotlottie.core.compose.ui.DotLottieAnimation
import com.lottiefiles.dotlottie.core.util.DotLottieSource
import com.example.pasada.ui.components.PasadaAlertDialog
import com.example.pasada.ui.theme.*
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntroductionScreen(
    onLoginSuccess: () -> Unit,
    onSignUpSuccess: () -> Unit
) {
    var showLoginSheet by remember { mutableStateOf(false) }
    var showSignUpSheet by remember { mutableStateOf(false) }

    val currentHour = LocalTime.now().hour
    val backgroundGradient = remember(currentHour) {
        val topColor = when (currentHour) {
            in 5..11 -> Color(0xFF5baa7a)
            in 12..17 -> Color(0xFF3fb84f)
            in 18..21 -> Color(0xFF85708a)
            else -> Color(0xFF2a2d3a)
        }
        val bottomColor = when (currentHour) {
            in 5..11 -> Color(0xFF439464)
            in 12..17 -> Color(0xFF26AB37)
            in 18..21 -> Color(0xFF705776)
            else -> Color(0xFF1C1F2E)
        }
        Brush.verticalGradient(
            0.0f to topColor,
            1.0f to bottomColor
        )
    }

    val animationValue = remember { Animatable(0f) }
    
    LaunchedEffect(Unit) {
        animationValue.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1200,
                easing = FastOutSlowInEasing
            )
        )
    }

    var showLocationDialog by remember { mutableStateOf(false) }
    var locationAskedOnce by remember { mutableStateOf(false) }
    val context = LocalContext.current
    
    val backgroundLocationLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            showLocationDialog = false
        }
    )

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            val granted = permissions.values.any { it }
            if (granted && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                val hasBg = ContextCompat.checkSelfPermission(
                    context, 
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                if (!hasBg) {
                    backgroundLocationLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                    return@rememberLauncherForActivityResult
                }
            }
            showLocationDialog = false
        }
    )
    
    LaunchedEffect(Unit) {
        if (!locationAskedOnce) {
            val hasFineLocation = ContextCompat.checkSelfPermission(
                context, 
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            
            val hasCoarseLocation = ContextCompat.checkSelfPermission(
                context, 
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            
            val hasBackgroundLocation = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                ContextCompat.checkSelfPermission(
                    context, 
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            } else {
                true
            }
            
            if (!hasFineLocation && !hasCoarseLocation || !hasBackgroundLocation) {
                showLocationDialog = true
            }
            locationAskedOnce = true
        }
    }

    if (showLocationDialog) {
        PasadaAlertDialog(
            title = "Location Access",
            message = "This app needs location access (including in the background) to pinpoint pickup points, find nearby drivers effectively, and update your ride status.",
            icon = Icons.Default.LocationOn,
            iconContentDescription = "Location Access",
            confirmText = "Allow",
            dismissText = "Cancel",
            onConfirm = {
                val hasFineLocation = ContextCompat.checkSelfPermission(
                    context, 
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                
                val hasCoarseLocation = ContextCompat.checkSelfPermission(
                    context, 
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                
                if (!hasFineLocation && !hasCoarseLocation) {
                    locationPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                    backgroundLocationLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                } else {
                    showLocationDialog = false
                }
            },
            onDismiss = { showLocationDialog = false },
            warningBoxText = "Background location access is required to track ongoing rides and driver matching."
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundGradient)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp)
                            .graphicsLayer {
                                alpha = animationValue.value
                                translationY = 24f * (1f - animationValue.value)
                            }
                    ) {
                        DotLottieAnimation(
                            source = DotLottieSource.Url("https://lottie.host/9d31fd2d-8b80-4fcc-8fd4-ca885c836dbf/P3eBf6g6Ms.lottie"),
                            autoplay = true,
                            loop = true,
                            speed = 3f,
                            useFrameInterpolation = false,
                            modifier = Modifier.background(Color.Transparent)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Kumusta!",
                            fontSize = 56.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFFF5F5F5),
                            fontFamily = ReadexProFontFamily,
                            lineHeight = 61.6.sp,
                            letterSpacing = (-1.0).sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Sakay ka na, boss!",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFFF5F5F5),
                            fontFamily = ReadexProFontFamily,
                            letterSpacing = 0.2.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp, vertical = 24.dp)
                        .graphicsLayer {
                            alpha = animationValue.value
                            translationY = 24f * (1f - animationValue.value)
                        }
                ) {
                    Button(
                        onClick = { showLoginSheet = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PasadaPrimary,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text(
                            text = "Log In",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { showSignUpSheet = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PasadaTransparentWhite,
                            contentColor = PasadaTextLight
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .border(
                                width = 1.5.dp,
                                color = PasadaBorderWhite,
                                shape = RoundedCornerShape(14.dp)
                            ),
                        shape = RoundedCornerShape(14.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                    ) {
                        Text(
                            text = "Create Account",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }

    if (showLoginSheet) {
        ModalBottomSheet(
            onDismissRequest = { showLoginSheet = false },
            containerColor = Color(0xFF121212),
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            LoginScreen(
                onNavigateBack = { showLoginSheet = false },
                onLoginSuccess = onLoginSuccess,
                onNavigateToForgotPassword = { /* TODO */ },
                onNavigateToSignUp = {
                    showLoginSheet = false
                    showSignUpSheet = true
                }
            )
        }
    }

    if (showSignUpSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSignUpSheet = false },
            containerColor = Color(0xFF121212),
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            SignUpScreen(
                onNavigateBack = { showSignUpSheet = false },
                onSignUpSuccess = onSignUpSuccess,
                onNavigateToLogin = {
                    showSignUpSheet = false
                    showLoginSheet = true
                }
            )
        }
    }
}
