package com.evan.dokan.ui.home.cart

import com.evan.dokan.data.db.entities.Cart
import com.evan.dokan.data.db.entities.Product

interface ICartListListener {
    fun cart(data:MutableList<Cart>?)
    fun onStarted()
    fun onEnd()
}