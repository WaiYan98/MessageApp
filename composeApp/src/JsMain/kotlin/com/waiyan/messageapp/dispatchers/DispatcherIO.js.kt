package com.waiyan.messageapp.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual fun dispatcherIO(): CoroutineDispatcher = Dispatchers.Default