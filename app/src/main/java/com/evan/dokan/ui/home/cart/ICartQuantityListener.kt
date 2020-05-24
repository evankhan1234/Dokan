package com.evan.dokan.ui.home.cart

interface ICartQuantityListener {
    fun onQuantity(quantity:Int,position:Int,productId:Int)
}