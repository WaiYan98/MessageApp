package com.waiyan.messageapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.google.firebase.FirebasePlatform
import com.waiyan.messageapp.screens.AuthenticationScreen
import dev.gitlive.firebase.FirebaseOptions

fun main() = application {

    FirebasePlatform.initializeFirebasePlatform(object : FirebasePlatform() {

        private val storage: MutableMap<String, String> = mutableMapOf()

        override fun clear(key: String) {
            storage.remove(key)
        }

        override fun log(msg: String) {
            println(msg)
        }

        override fun retrieve(key: String): String? {
            return storage[key]
        }

        override fun store(key: String, value: String) {
            storage[key] = value
        }
    })

    val option = FirebaseOptions(
        applicationId = "1:683211989560:web:4df7e24cc27e32fb7ffccd",
        apiKey = "AIzaSyD5ULxZoELXl8T-fOkYU-Wklmym7e5m-x8",
        projectId = "messageapp-b1625"
    )

    Window(
        onCloseRequest = ::exitApplication,
        title = "MessageApp",
    ) {
        AuthenticationScreen()
    }
}