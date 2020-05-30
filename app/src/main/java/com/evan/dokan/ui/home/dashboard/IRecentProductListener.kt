package com.evan.dokan.ui.home.dashboard

import com.evan.dokan.data.db.entities.Category
import com.evan.dokan.data.db.entities.Product

interface IRecentProductListener {
    fun product(data:MutableList<Product>?)
    fun onStarted()
    fun onEnd()
}