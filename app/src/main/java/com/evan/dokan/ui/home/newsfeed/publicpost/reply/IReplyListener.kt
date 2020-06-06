package com.evan.dokan.ui.home.newsfeed.publicpost.reply

import com.evan.dokan.data.db.entities.Comments
import com.evan.dokan.data.db.entities.Reply

interface IReplyListener {
    fun load(data:MutableList<Reply>?)
    fun onStarted()
    fun onEnd()
}