package com.evan.dokan.ui.auth.interfaces

interface Listener {
    fun Success(result: String)
    fun Failure(result: String)
}