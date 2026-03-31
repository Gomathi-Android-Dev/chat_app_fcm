package com.anniyam.chatapp.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun LoginRoute(navController: NavController, viewModel: AuthViewModel = hiltViewModel()) {


    LaunchedEffect(viewModel.isLoginSuccessful) {
        if (viewModel.isLoginSuccessful) {
            navController.navigate("user_list") {
                popUpTo("login") { inclusive = true }
            }
            viewModel.onNavigated()
        }
    }
    LoginScreen(viewModel)
}