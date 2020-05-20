package com.evan.dokan.ui.home.dashboard.product

import com.evan.dokan.data.db.entities.Product
import com.evan.dokan.data.db.entities.Shop

interface IProductCategoryWiseListener {
    fun show(data:MutableList<Product>)
    fun started()
    fun failure(message:String)
    fun end()
    fun exit()
}