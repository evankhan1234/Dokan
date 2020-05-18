package com.evan.dokan.ui.auth.interfaces

import com.evan.dokan.data.db.entities.User
import com.evan.dokan.data.db.entities.Users


interface AuthListener {
    fun onStarted()
    fun onSuccess(user: Users)
    fun onFailure(message: String)

}