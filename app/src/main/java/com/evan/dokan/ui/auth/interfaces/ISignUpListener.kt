package com.evan.dokan.ui.auth.interfaces

interface ISignUpListener {
    fun onStarted()
    fun onSuccess(message:String)
    fun onFailure(message:String)
    fun onEnd()
}