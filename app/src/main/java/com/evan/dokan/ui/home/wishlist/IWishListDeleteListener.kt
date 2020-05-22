package com.evan.dokan.ui.home.wishlist

import com.evan.dokan.data.db.entities.Product

interface IWishListDeleteListener {
    fun onProduct(product: Product)
}