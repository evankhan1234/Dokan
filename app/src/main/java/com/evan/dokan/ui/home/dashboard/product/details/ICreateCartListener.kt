package com.evan.dokan.ui.home.dashboard.product.details

interface ICreateCartListener {
    fun onSuccessCart(message:String)
    fun onStarted()
    fun onEnd()
    fun onFailure(message: String)
}