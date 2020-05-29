package com.evan.dokan.ui.home.settings.profile

import com.evan.dokan.data.db.entities.Users

interface IProfileUpdateListener {
    fun onStarted()
    fun onEnd()
    fun onUser(message: String)
    fun onFailure(message: String)
}