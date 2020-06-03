package com.evan.dokan.ui.home.newsfeed.publicpost

import com.evan.dokan.data.db.entities.Post

interface IPublicPostUpdateListener {
    fun onUpdate(post: Post)
}