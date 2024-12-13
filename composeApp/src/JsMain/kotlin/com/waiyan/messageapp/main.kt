package com.waiyan.messageapp

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import androidx.compose.ui.window.ComposeViewport
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseApp
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.initialize
import kotlinx.browser.document
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {

    onWasmReady {
        CanvasBasedWindow(title = "MessageApp") {
            Firebase.initialize(
                options = FirebaseOptions(
                    applicationId = "1:683211989560:web:4df7e24cc27e32fb7ffccd",
                    apiKey = "AIzaSyD5ULxZoELXl8T-fOkYU-Wklmym7e5m-x8",
                    projectId = "messageapp-b1625"
                )
            )
            Login()
        }
    }
}