package com.waiyan.messageapp.remote

import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.UserInfo

interface AuthenticationService {

    suspend fun createUser(email: String, password: String): FirebaseUser

    suspend fun signIn(email: String, password: String): FirebaseUser

    suspend fun getCurrentUser(): FirebaseUser?
}