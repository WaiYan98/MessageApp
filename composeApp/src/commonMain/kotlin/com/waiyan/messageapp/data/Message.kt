package com.waiyan.messageapp.data

data class Message(
    val text: String,
    val senderId: String = "",
    val isSentByCurrentUser: Boolean = false,
    val date: String,
    val time: String
)