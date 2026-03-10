package com.example.pasada.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pasada.Greeting
import com.example.pasada.ui.screens.IntroductionScreen
import com.example.pasada.ui.viewmodel.AuthViewModel

@Composable
fun PasadaApp() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val authUiState by authViewModel.uiState.collectAsState()

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
                authUiState = authUiState,
                onSignIn = authViewModel::signIn,
                onSignUp = authViewModel::signUp,
                onClearError = authViewModel::clearError,
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
            Greeting(name = "Para! User")
        }
    }
}
