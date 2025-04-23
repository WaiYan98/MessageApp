package com.waiyan.messageapp.remote

import com.waiyan.messageapp.data.RawMessage
import dev.gitlive.firebase.database.DatabaseReference
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MessageServiceImpl(
    private val reference: DatabaseReference,
    private val dispatcher: CoroutineDispatcher
) : MessageService {

    override suspend fun sendMessage(rawMessage: RawMessage) = withContext(dispatcher) {
        try {
            val key = reference.push().key
            if (key != null) {
                reference.child(key).setValue(rawMessage)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun readMessage(): Flow<List<RawMessage>> =
        withContext(dispatcher) {
            return@withContext try {
                val rawMessages = reference.valueEvents
                    .map { dataSnapshot ->
                        val messageMap = dataSnapshot.value() as Map<String, RawMessage>
                        messageMap.values.toList()
                            .sortedWith { a, b -> a.time.toLong().compareTo(b.time.toLong()) }
                    }
                println("ReadMessage => $rawMessages")
                rawMessages
            } catch (e: Exception) {
                throw e
            }
        }

}