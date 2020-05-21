package com.evan.dokan.ui.home.dashboard.category

import com.evan.dokan.data.db.entities.Category

interface ICategoryUpdateListener {
    fun onUpdate(category: Category)
}