package com.evan.dokan.ui.home.newsfeed.publicpost.comments

interface ICommentsPostListener {
    fun onStarted()
    fun onSuccess(message:String)
    fun onFailure(message:String)
    fun onEnd()
}