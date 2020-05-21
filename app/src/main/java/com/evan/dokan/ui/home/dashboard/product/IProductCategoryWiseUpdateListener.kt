package com.evan.dokan.ui.home.dashboard.product

import com.evan.dokan.data.db.entities.Product


interface IProductCategoryWiseUpdateListener {
    fun onUpdate(product: Product)
}