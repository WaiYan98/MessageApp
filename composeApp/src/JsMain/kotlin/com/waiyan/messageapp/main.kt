package com.waiyan.messageapp

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.waiyan.messageapp.di.initKoin
import com.waiyan.messageapp.screens.MessageApp
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.initialize
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin()
    onWasmReady {
        CanvasBasedWindow(title = "MessageApp") {
            Firebase.initialize(
                options = FirebaseOptions(
                    applicationId = "1:683211989560:web:4df7e24cc27e32fb7ffccd",
                    apiKey = "AIzaSyD5ULxZoELXl8T-fOkYU-Wklmym7e5m-x8",
                    projectId = "messageapp-b1625"
                )
            )
            MessageApp()
        }
    }
}