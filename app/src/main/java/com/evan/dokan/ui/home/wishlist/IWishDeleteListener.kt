package com.evan.dokan.ui.home.wishlist

interface IWishDeleteListener {
    fun onSuccess(message:String)
    fun onStarted()
    fun onEnd()
    fun onFailure(message: String)
}