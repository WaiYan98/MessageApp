package com.waiyan.messageapp.screens.message

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.waiyan.messageapp.data.Message
import com.waiyan.messageapp.screens.authentication.showError
import com.waiyan.messageapp.util.DateTime
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MessageScreen(
    viewModel: MessageViewModel = koinViewModel()
) {

    var message by remember { mutableStateOf("") }
    val uiState by viewModel.messageUiState.collectAsStateWithLifecycle()
    val lazyListState = rememberLazyListState()

    LaunchedEffect(key1 = uiState.success) {
        if (uiState.success.isNotEmpty()) {
            println("MapSize=> ${viewModel.countMapSize(uiState.success)}")
            lazyListState.animateScrollToItem(viewModel.countMapSize(uiState.success))
        }
    }

    if (uiState.error != null) {
        showError(uiState.error.toString())
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(
                top = 16.dp,
                start = 24.dp,
                end = 24.dp,
                bottom = 16.dp
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(0.9f),
            state = lazyListState
        ) {
            if (uiState.success.isNotEmpty()) {
                uiState.success.entries.toList().forEach {
                    item {
                        ShowDate(it.key)
                    }
                    items(it.value) { message ->
                        Conversation(message)
                        Spacer(modifier = Modifier.height(24.dp))
                        println("isSentByCurrentUserUi => $it")
                    }
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            InputMessage(message, onValueChange = {
                message = it
            }) {
                viewModel.sendMessage(message)
                message = ""
            }
        }
    }

}

@Composable
fun Conversation(
    message: Message
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (message.isSentByCurrentUser) Arrangement.End else Arrangement.Start

    ) {

        if (!message.isSentByCurrentUser) {
            Box(
                modifier = Modifier.size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Red)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray),
            horizontalAlignment = Alignment.End
        ) {

            Box(
                modifier = Modifier
                    .fillMaxHeight(0.9f)
            ) {
                Text(
                    modifier = Modifier
                        .padding(
                            top = 8.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 8.dp
                        )
                        .align(Alignment.Center),
                    text = message.text,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight(0.1f)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.BottomEnd)
                        .padding(
                            top = 8.dp,
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 8.dp
                        ),
                    text = message.time,
                    style = TextStyle(
                        color = Color.Blue,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
fun ShowDate(date: String) {

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = date,
            style = TextStyle(
                color = Color.Black,
                fontSize = 16.sp
            )
        )
    }
}

@Composable
fun InputMessage(
    message: String,
    onValueChange: (String) -> Unit,
    onClickSend: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            value = message,
            onValueChange = onValueChange,
            placeholder = {
                Text("Type a message")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.MailOutline,
                    contentDescription = "mail_icon"
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        onClickSend()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = "send_icon"
                    )
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                leadingIconColor = Color.Blue,
                trailingIconColor = Color.Blue
            )
        )
    }
}