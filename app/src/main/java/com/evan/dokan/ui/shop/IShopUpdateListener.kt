package com.evan.dokan.ui.shop

import com.evan.dokan.data.db.entities.Shop

interface IShopUpdateListener {
    fun onUpdate(shop: Shop)
}