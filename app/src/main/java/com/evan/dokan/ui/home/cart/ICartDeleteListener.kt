package com.evan.dokan.ui.home.cart

import com.evan.dokan.data.db.entities.Cart
import com.evan.dokan.data.db.entities.Product

interface ICartDeleteListener {
    fun onCart(cart: Cart)
}