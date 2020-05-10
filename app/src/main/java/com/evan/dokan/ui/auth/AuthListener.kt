package com.evan.dokan.ui.auth

import com.evan.dokan.data.db.entities.User


interface AuthListener {
    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(message: String)

}