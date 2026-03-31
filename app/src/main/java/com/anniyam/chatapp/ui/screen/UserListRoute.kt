package com.anniyam.chatapp.ui.screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun UserListRoute(navController : NavHostController,viewModel: UserViewModel = hiltViewModel()) {
    UserListScreen(
        onUserClick = { user ->
            // Navigate to chat screen passing the user's ID
            navController.navigate("chat/${user.uid}")
        }
    )
}