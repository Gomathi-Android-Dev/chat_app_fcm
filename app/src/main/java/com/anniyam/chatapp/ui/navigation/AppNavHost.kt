package com.anniyam.chatapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anniyam.chatapp.ui.screen.ChatScreen
import com.anniyam.chatapp.ui.screen.LoginRoute
import com.anniyam.chatapp.ui.screen.LoginScreen
import com.anniyam.chatapp.ui.screen.UserListRoute
import com.anniyam.chatapp.ui.screen.UserListScreen

@Composable
fun AppNavHost(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login" // Set login as the starting screen
    ) {
        composable("login") {
            // Replace this with your actual LoginScreen Composable
            LoginRoute(navController)
        }
        composable("user_list") {
            UserListRoute(navController)
        }
        composable("chat/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            ChatScreen(userId = userId, onBackClick = { navController.popBackStack() })
        }
    }
}