package com.evan.dokan.ui.shop

import com.evan.dokan.data.db.entities.Shop

interface IShopListener {
    fun show(data:MutableList<Shop>)
    fun started()
    fun failure(message:String)
    fun end()
    fun exit()
}