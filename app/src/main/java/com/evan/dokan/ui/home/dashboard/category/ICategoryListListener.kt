package com.evan.dokan.ui.home.dashboard.category

import com.evan.dokan.data.db.entities.Category

interface ICategoryListListener {
    fun category(data:MutableList<Category>?)
    fun onStarted()
    fun onEnd()
}