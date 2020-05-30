package com.evan.dokan.ui.home.settings

import com.evan.dokan.data.db.entities.Users

interface IUserListener {
    fun onStarted()
    fun onEnd()
    fun onUser(users: Users)
}