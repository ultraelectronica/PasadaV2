package com.example.pasada.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
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
        composable(
            route = "introduction",
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(500)) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(500)) },
            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(500)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(500)) }
        ) {
            IntroductionScreen(
                onLoginSuccess = { 
                    navController.navigate("home") {
                        popUpTo("introduction") { inclusive = true }
                    }
                },
                onSignUpSuccess = { 
                    navController.navigate("home") {
                        popUpTo("introduction") { inclusive = true }
                    }
                }
            )
        }
        composable(
            route = "home",
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(500)) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(500)) },
            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(500)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(500)) }
        ) {
            Greeting(name = "Pasada User")
        }
    }
}
