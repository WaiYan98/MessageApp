package com.waiyan.messageapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform