package com.waiyan.messageapp.screens.message

import com.waiyan.messageapp.data.Message

data class MessageUiState(
    val success: Map<String, List<Message>> = emptyMap(),
    val isLoading: Boolean = false,
    val error: String? = null
)