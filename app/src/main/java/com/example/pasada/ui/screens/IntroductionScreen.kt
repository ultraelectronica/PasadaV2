package com.example.pasada.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.pasada.ui.components.PasadaAlertDialog
import com.example.pasada.ui.theme.ReadexProFontFamily
import com.example.pasada.ui.theme.PasadaPrimary
import com.example.pasada.ui.theme.PasadaSecondary
import com.example.pasada.ui.theme.PasadaTextLight
import com.example.pasada.ui.theme.PasadaTextDim
import com.example.pasada.ui.theme.PasadaTransparentWhite
import com.example.pasada.ui.theme.PasadaBorderWhite
import java.time.LocalTime

@Composable
fun IntroductionScreen(
    onLoginClick: () -> Unit,
    onCreateAccountClick: () -> Unit
) {
    val currentHour = LocalTime.now().hour
    val backgroundGradient = remember(currentHour) {
        when (currentHour) {
            in 5..11 -> Brush.verticalGradient(listOf(Color(0xFF236078), Color(0xFF439464)))
            in 12..17 -> Brush.verticalGradient(listOf(Color(0xFFCFA425), Color(0xFF26AB37)))
            in 18..21 -> Brush.verticalGradient(listOf(Color(0xFFB45F4F), Color(0xFF705776)))
            else -> Brush.verticalGradient(listOf(Color(0xFF2E3B4E), Color(0xFF1C1F2E)))
        }
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
                    .padding(innerPadding)
                    .padding(horizontal = 32.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))
                
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.graphicsLayer {
                        alpha = animationValue.value
                        translationY = size.height * 0.3f * (1f - animationValue.value)
                    }
                ) {
                    Text(
                        text = "Kumusta!",
                        fontSize = 56.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = PasadaTextLight,
                        fontFamily = ReadexProFontFamily,
                        lineHeight = 61.6.sp,
                        letterSpacing = (-1.0).sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Sakay ka na, boss!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = PasadaTextDim,
                        fontFamily = ReadexProFontFamily,
                        letterSpacing = 0.2.sp,
                        textAlign = TextAlign.Center
                    )
                }
                
                Spacer(modifier = Modifier.weight(1f))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer {
                            alpha = animationValue.value
                            translationY = size.height * 0.3f * (1f - animationValue.value)
                        }
                ) {
                    Button(
                        onClick = onLoginClick,
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
                        onClick = onCreateAccountClick,
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
}

