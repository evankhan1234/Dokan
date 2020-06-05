package com.evan.dokan.ui.home.newsfeed.publicpost.comments

import com.evan.dokan.data.db.entities.Comments
import com.evan.dokan.data.db.entities.Product

interface ICommentsListener {
    fun load(data:MutableList<Comments>?)
    fun onStarted()
    fun onEnd()
}