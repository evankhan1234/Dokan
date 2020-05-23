package com.evan.dokan.ui.home.cart

interface ICartListDeleteListener {
    fun onSuccess(message:String)
    fun onStarted()
    fun onEnd()
    fun onFailure(message: String)
}