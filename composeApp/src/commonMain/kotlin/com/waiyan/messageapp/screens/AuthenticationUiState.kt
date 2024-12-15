package com.waiyan.messageapp.screens

import dev.gitlive.firebase.auth.FirebaseUser

data class AuthenticationUiState(
     val success: FirebaseUser? = null,
     val isLoading: Boolean = false,
     val onError: String? = null

)
