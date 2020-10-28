package com.chatbot.smartbot.utils

import android.annotation.SuppressLint
import com.chatbot.smartbot.utils.Constants.OPEN_GOOGLE
import com.chatbot.smartbot.utils.Constants.OPEN_SEARCH
import java.lang.Exception
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.sql.Date
import java.util.*

object SmartBotResponse {

    @SuppressLint("SimpleDateFormat")
    fun response(mMessage: String): String {
        val random = (0..2).random()
        val message = mMessage.toLowerCase(Locale.ROOT)
        return when {
            //flips a coin
            message.contains("flip") && message.contains("coin") -> {
                val coinFaces = (0..1).random()
                val result = if (coinFaces == 0) "head" else "tail"
                "Coin is flipped and it shows $result"
            }

            //equation solution
            message.contains("solve")->{
                val equation: String? = message.substringAfter("solve")
                try{
                    val result = AlgorithmMathSolution.solveEquation(equation ?: "0")
                    "$result"
                }catch (e: Exception){
                    "Sorry, I can't solve this"
                }
            }

            //reply to Hello
            message.contains("hello") || message.contains("hi")->{
                when(random){
                    0->"Hello"
                    1->"Hi!"
                    2->"Hi, how can I help?"
                    else -> "Error"
                }
            }

            //reply to How are you
            message.contains("how are you")->{
                when(random){
                    0->"I'm doing good, thanks!"
                    1->"I'm hungry..."
                    2->"I'm fine, thanks. How about you?"
                    else-> "Error"
                }
            }

            //reply to what is time
            message.contains("time")&&message.contains("?")->{
                val timeStamp = Timestamp(System.currentTimeMillis())
                val sdf = SimpleDateFormat("dd/mm/yyyy HH:mm")
                val date = sdf.format(Date(timeStamp.time))
                date.toString()
            }

            // open google
            message.contains("open")&&message.contains("google")->{
                OPEN_GOOGLE
            }

            //open search
            message.contains("search")->{
                OPEN_SEARCH
            }

            //When the programm doesn't understand...
            else -> {
                when (random) {
                    0 -> "I don't understand"
                    1 -> "Ask me something different"
                    2 -> "Grrrrr !!!"
                    else -> "Sorry, seems there's an error"
                }
            }
        }
    }
}