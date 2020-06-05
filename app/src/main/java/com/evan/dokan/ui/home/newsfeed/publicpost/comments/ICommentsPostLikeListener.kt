package com.evan.dokan.ui.home.newsfeed.publicpost.comments

interface ICommentsPostLikeListener {
    fun onCount(count:Int?,type:Int,id:Int)
}