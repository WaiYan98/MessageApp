package com.waiyan.messageapp.di

import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(shareModule, platformModule)
    }
}