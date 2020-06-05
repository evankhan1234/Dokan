package com.evan.dokan.ui.home.newsfeed.publicpost.comments

import com.evan.dokan.data.db.entities.Comments
import com.evan.dokan.data.db.entities.Product

interface ICommentsUpdateListener {
    fun onShow(comments: Comments)
}