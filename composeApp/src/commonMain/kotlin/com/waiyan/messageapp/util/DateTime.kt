package com.waiyan.messageapp.util

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object DateTime {

    fun getCurrentTime(): String {
        val timestamp = Clock.System.now().toEpochMilliseconds()
        println("Time => $timestamp")
        return timestamp.toString()
    }

    fun getLocalDateTime(timestamp: Long): LocalDateTime {
        return Instant.fromEpochMilliseconds(timestamp)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    }

    fun getReadableDate(localDateTime: LocalDateTime): String {
        val year = localDateTime.year
        val month = localDateTime.monthNumber
        val day = localDateTime.dayOfMonth
        return "$day/$month/$year"
    }


    fun getReadableTime(localDateTime: LocalDateTime): String {
        val second = localDateTime.second
        val hour = localDateTime.hour
        val minute = localDateTime.minute
        return "$hour : $minute : $second s"
    }

}