package com.waiyan.messageapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.waiyan.messageapp.screens.AuthenticationScreen
import com.waiyan.messageapp.screens.MessageApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MessageApp()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}