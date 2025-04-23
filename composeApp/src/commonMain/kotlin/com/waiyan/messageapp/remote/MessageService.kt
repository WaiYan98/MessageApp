package com.waiyan.messageapp.remote

import com.waiyan.messageapp.data.RawMessage
import kotlinx.coroutines.flow.Flow

interface MessageService {

    suspend fun sendMessage(rawMessage: RawMessage)
    suspend fun readMessage(): Flow<List<RawMessage>>
}