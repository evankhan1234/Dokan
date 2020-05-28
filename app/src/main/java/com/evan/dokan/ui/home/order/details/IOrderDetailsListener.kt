package com.evan.dokan.ui.home.order.details

import com.evan.dokan.data.db.entities.Cart
import com.evan.dokan.data.db.entities.OrderDetails

interface IOrderDetailsListener {
    fun order(data:MutableList<OrderDetails>?)
    fun onStarted()
    fun onEnd()
}