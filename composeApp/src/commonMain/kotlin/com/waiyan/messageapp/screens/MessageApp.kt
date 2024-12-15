package com.waiyan.messageapp.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavGraphNavigator
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class Route(title: String) {
    LoginScreen("login_screen"),
    MessageScreen("message_screen")

}

@Composable
fun MessageApp() {
    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = Route.LoginScreen.name
    ) {
        composable(route = Route.LoginScreen.name) {
            AuthenticationScreen {
                navController.navigate(Route.MessageScreen.name)
            }
        }

        composable(route = Route.MessageScreen.name) {
            MessageScreen()
        }
    }

}
