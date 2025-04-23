package com.waiyan.messageapp.screens.message

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.waiyan.messageapp.data.Message
import com.waiyan.messageapp.data.RawMessage
import com.waiyan.messageapp.remote.MessageService
import com.waiyan.messageapp.screens.MessageScreen
import com.waiyan.messageapp.util.DateTime
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MessageViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val messageService: MessageService
) : ViewModel() {
    private var userId: String = ""

    init {
        val messageScreenRoute = savedStateHandle.toRoute<MessageScreen>()
        userId = messageScreenRoute.userId!!
        readMessage()
    }

    private val _messageUiState = MutableStateFlow(MessageUiState())
    val messageUiState: StateFlow<MessageUiState> = _messageUiState

    fun sendMessage(text: String) = viewModelScope.launch {
        try {
            val currentTime = DateTime.getCurrentTime()
            if (userId.isNotBlank()) {
                val rawMessage = RawMessage(
                    text = text,
                    time = currentTime,
                    senderId = userId
                )
                messageService.sendMessage(rawMessage)
            } else {
                _messageUiState.emit(MessageUiState(error = "Something Wrong"))
            }
        } catch (e: Exception) {
            _messageUiState.emit(MessageUiState(error = e.message))
        }
    }

    private fun readMessage() = viewModelScope.launch {
        messageService.readMessage()
            .collect {
                println("ReadMessage => ${it.size}")
                val messages = rawMessageToMessage(it)
                println("messages = > $messages")
                val messageMap = groupedMessageByDate(messages)
                _messageUiState.emit(MessageUiState(success = messageMap))
            }
    }


    private fun getDate(timestamp: Long): String {
        val localDateTime = DateTime.getLocalDateTime(timestamp)
        val date = DateTime.getReadableDate(localDateTime)
        return date
    }

    private fun getTime(timestamp: Long): String =
        DateTime.getReadableTime(DateTime.getLocalDateTime(timestamp))


    fun countMapSize(map: Map<String, List<Message>>) = map.keys.size + map.values.sumOf { it.size }

    private fun groupedMessageByDate(messageList: List<Message>): Map<String, List<Message>> {
        val dateMessagesMap: MutableMap<String, List<Message>> = mutableMapOf()
        var messages: MutableList<Message> = mutableListOf()
        var initialDate = messageList[0].date
        messageList.forEach { message ->
            val currentDate = message.date
            if (initialDate == currentDate) {
                messages.add(message)
            } else {
                dateMessagesMap[initialDate] = messages
                initialDate = currentDate
                messages = mutableListOf()
                messages.add(message)
            }
        }
        if (messages.isNotEmpty()) {
            dateMessagesMap[initialDate] = messages
        }
        return dateMessagesMap
    }

    private fun rawMessageToMessage(rawMessageList: List<RawMessage>): List<Message> {
        return rawMessageList.map { raw ->
            Message(
                text = raw.text,
                senderId = raw.senderId,
                isSentByCurrentUser = userId == raw.senderId,
                date = getDate(raw.time.toLong()),
                time = getTime(raw.time.toLong())
            )
        }
    }

}