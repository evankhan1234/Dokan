package com.evan.dokan.ui.home.wishlist

interface IWishListCreateListener {
    fun onSuccess(message:String)
    fun onStarted()
    fun onEnd()
    fun onFailure(message: String)
}