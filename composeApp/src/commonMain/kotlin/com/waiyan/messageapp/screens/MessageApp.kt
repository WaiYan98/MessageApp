package com.waiyan.messageapp.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.waiyan.messageapp.dispatchers.dispatcherIO
import com.waiyan.messageapp.remote.AuthenticationServiceImpl
import com.waiyan.messageapp.remote.MessageServiceImpl
import com.waiyan.messageapp.screens.authentication.AuthenticationScreen
import com.waiyan.messageapp.screens.message.MessageScreen
import com.waiyan.messageapp.screens.message.MessageViewModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.database.database
import kotlinx.serialization.Serializable

@Serializable
data object LoginScreen
@Serializable
data class MessageScreen(val userId: String? = null)

@Composable
fun MessageApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LoginScreen
    ) {
        composable<LoginScreen> {
            AuthenticationScreen { userId ->
                navController.navigate(MessageScreen(userId))
            }
        }

        composable<MessageScreen> { navBackStack ->
            val uid = navBackStack.toRoute<MessageScreen>().userId
            println("UserIdDelivery -> $uid")
            MessageScreen()
        }
    }

}
