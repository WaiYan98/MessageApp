package com.waiyan.messageapp

import android.app.Application
import com.waiyan.messageapp.di.platformModule
import com.waiyan.messageapp.di.shareModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(shareModule, platformModule)
        }
    }
}