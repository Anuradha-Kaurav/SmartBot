package com.chatbot.smartbot.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chatbot.smartbot.R
import com.chatbot.smartbot.data.Message
import com.chatbot.smartbot.utils.Constants
import kotlinx.android.synthetic.main.message_item.view.*

class MessageAdapter: RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    val messageList = mutableListOf<Message>()
    inner class MessageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                //Remove message on the item clicked
                messageList.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false))
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val msg = messageList[position]

        when(msg.id){
            Constants.SEND_ID->{
                holder.itemView.tv_message.apply {
                    text = msg.message
                    visibility = View.VISIBLE
                }
                holder.itemView.tv_bot_message.visibility = View.GONE
            }
            Constants.RECEIVE_ID->{
                holder.itemView.tv_bot_message.apply {
                    text = msg.message
                    visibility = View.VISIBLE
                }
                holder.itemView.tv_message.visibility = View.GONE
            }
        }
    }

    fun insertMessage(message: Message){
        this.messageList.add(message)
        notifyItemInserted(messageList.size)
    }
}