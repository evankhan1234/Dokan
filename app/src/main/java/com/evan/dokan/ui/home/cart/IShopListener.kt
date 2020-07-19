package com.evan.dokan.ui.home.cart

import com.evan.dokan.data.db.entities.Shop


interface IShopListener {
    fun onShow(shop: Shop)
}