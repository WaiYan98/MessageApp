package com.waiyan.messageapp.remote

import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthenticationServiceImpl(private val auth: FirebaseAuth) : AuthenticationService {

    override suspend fun createUser(
        email: String,
        password: String
    ): FirebaseUser = withContext(Dispatchers.Default) {
        return@withContext try {
            auth.createUserWithEmailAndPassword(email, password).user!!
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun signIn(email: String, password: String): FirebaseUser =
        withContext(Dispatchers.Default) {
            return@withContext try {
                auth.signInWithEmailAndPassword(email, password).user!!
            } catch (e: Exception) {
                throw e
            }
        }

    override suspend fun getCurrentUser(): FirebaseUser? =
        withContext(Dispatchers.Default) {
            return@withContext auth.currentUser
        }
}