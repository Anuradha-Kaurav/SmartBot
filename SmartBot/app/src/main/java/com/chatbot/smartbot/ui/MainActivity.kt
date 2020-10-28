package com.chatbot.smartbot.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.chatbot.smartbot.R
import com.chatbot.smartbot.data.Message
import com.chatbot.smartbot.utils.Constants.OPEN_GOOGLE
import com.chatbot.smartbot.utils.Constants.OPEN_SEARCH
import com.chatbot.smartbot.utils.Constants.RECEIVE_ID
import com.chatbot.smartbot.utils.Constants.SEND_ID
import com.chatbot.smartbot.utils.SmartBotResponse
import com.chatbot.smartbot.utils.Time
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var messageAdapter: MessageAdapter
    private val botNameList = listOf<String>("Alex", "Fransis", "Joe", "Maria")
    var messagesList = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
        handleClickEvents()

        val random = (0..3).random()
        customBotResponse("Hello! Today you're speaking with ${botNameList[random]}, how may I help you ?")
    }

    fun initRecyclerView(){
        messageAdapter = MessageAdapter()
        rv_messages.adapter = messageAdapter
        rv_messages.layoutManager = LinearLayoutManager(applicationContext)
    }

    override fun onStart() {
        super.onStart()
        //Scroll to bottom if there are msgs when re-opening the app
        GlobalScope.launch {
            delay(100)
            withContext(Dispatchers.Main){
                rv_messages.scrollToPosition(messageAdapter.itemCount - 1)
            }
        }
    }

    fun customBotResponse(message: String){
        GlobalScope.launch {
            delay(100)
            withContext(Dispatchers.Main){
                val timeStamp = Time.timeStamp()
                messagesList.add(Message(message, RECEIVE_ID, timeStamp))
                messageAdapter.insertMessage(Message(message, RECEIVE_ID, timeStamp))
                rv_messages.scrollToPosition(messageAdapter.itemCount - 1)
            }
        }
    }

    fun handleClickEvents(){
        //send message
         btn_send.setOnClickListener{
             sendMessage()
         }

        //scroll back to correct position when user clicks on text view
        et_message.setOnClickListener{
            GlobalScope.launch {
                delay(100)
                withContext(Dispatchers.Main){
                    rv_messages.scrollToPosition(messageAdapter.itemCount - 1)
                }
            }
        }
    }

    fun sendMessage() {
        val message = et_message.text.toString()
        val timeStamp = Time.timeStamp()

        if(message.isNotEmpty()) {
            // add to the message list
            messagesList.add(Message(message, SEND_ID, timeStamp))
            et_message.setText("")

            messageAdapter.insertMessage(Message(message, SEND_ID, timeStamp))
            rv_messages.scrollToPosition(messageAdapter.itemCount - 1)

            botResponse(message)
        }
    }

    fun botResponse(message: String) {
        val timeStamp = Time.timeStamp()
        GlobalScope.launch {
            delay(100)
            withContext(Dispatchers.Main) {
                //get the response from bot
                val botResponse = SmartBotResponse.response(message)

                //add to message list
                messagesList.add(Message(botResponse, RECEIVE_ID, timeStamp))
                //Inserts our message into the adapter
                messageAdapter.insertMessage(Message(botResponse, RECEIVE_ID, timeStamp))
                rv_messages.scrollToPosition(messageAdapter.itemCount - 1)

                when(botResponse) {
                    OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String? = message.substringAfterLast("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }
                }
            }
        }
    }
}