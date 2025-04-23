package com.waiyan.messageapp.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

actual val platformModule = module {
    single<CoroutineDispatcher> { Dispatchers.IO }
}