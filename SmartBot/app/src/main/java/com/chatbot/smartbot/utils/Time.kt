package com.chatbot.smartbot.utils

import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

object Time {

    fun timeStamp(): String{
        val sdf = SimpleDateFormat("HH:mm")
        val timeStamp = Timestamp(System.currentTimeMillis())
        val time = sdf.format(Date(timeStamp.time))
        return time.toString()
    }
}