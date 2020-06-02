package com.evan.dokan.ui.home.newsfeed.ownpost

import com.evan.dokan.data.db.entities.Post
import com.evan.dokan.data.db.entities.Shop

interface IOwnPostUpdatedListener {
    fun onUpdate(post: Post)
}