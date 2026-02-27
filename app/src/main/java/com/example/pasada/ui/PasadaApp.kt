package com.example.pasada.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pasada.Greeting
import com.example.pasada.ui.screens.IntroductionScreen

@Composable
fun PasadaApp() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = "introduction",
        modifier = Modifier.fillMaxSize()
    ) {
        composable("introduction") {
            IntroductionScreen(
                onLoginClick = { navController.navigate("loginAccount") },
                onCreateAccountClick = { navController.navigate("createAccount") }
            )
        }
        composable("loginAccount") {
            Greeting(name = "Login Screen Placeholder")
        }
        composable("createAccount") {
            Greeting(name = "Create Account Placeholder")
        }
        composable("home") {
            Greeting(name = "Pasada User")
        }
    }
}
