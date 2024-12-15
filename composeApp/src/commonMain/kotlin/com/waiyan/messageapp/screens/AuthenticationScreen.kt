package com.waiyan.messageapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.waiyan.messageapp.remote.AuthenticationServiceImpl
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth

@Composable
fun AuthenticationScreen(
    goToMessageScreen: () -> Unit
) {

    val authenticationService = AuthenticationServiceImpl(Firebase.auth)
    val viewModel: AuthenticationViewModel =
        viewModel { AuthenticationViewModel(authenticationService) }
    var email: String by remember { mutableStateOf("") }
    var password: String by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = uiState.success) {
        if (uiState.success != null) {
            goToMessageScreen()
        }
    }

    if (uiState.isLoading) {
       ShowLoading()
    }

    if (uiState.onError != null) {
        showError(uiState.onError.toString())
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(
                start = 24.dp,
                end = 24.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Message App",
            style = TextStyle(
                color = Color.Blue,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            placeholder = {
                Text("Email")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "email_icon"
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                leadingIconColor = Color.Blue,
                placeholderColor = Color.DarkGray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            placeholder = {
                Text("Password")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "email_icon"
                )
            },
            trailingIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "key_icon"
                    )
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                leadingIconColor = Color.Blue,
                trailingIconColor = Color.Blue,
                placeholderColor = Color.DarkGray
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                viewModel.createUser(email, password)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
        ) {
            Text(
                "Login",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

    }

}

@Composable
fun ShowLoading() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun showError(result: String) {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            result,
            style = TextStyle(color = Color.Red),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}