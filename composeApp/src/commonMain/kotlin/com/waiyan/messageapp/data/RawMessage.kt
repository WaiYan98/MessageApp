package com.waiyan.messageapp.data

import kotlinx.serialization.Serializable

@Serializable
data class RawMessage (
    val text: String,
    val time: String = "1734600446",
    val senderId: String = "",
)