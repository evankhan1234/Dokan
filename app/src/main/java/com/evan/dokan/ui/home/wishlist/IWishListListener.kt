package com.evan.dokan.ui.home.wishlist

import com.evan.dokan.data.db.entities.Product

interface IWishListListener {
    fun wish(data:MutableList<Product>?)
    fun onStarted()
    fun onEnd()
}