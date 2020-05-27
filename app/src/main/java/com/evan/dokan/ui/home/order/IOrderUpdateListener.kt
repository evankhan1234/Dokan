package com.evan.dokan.ui.home.order

import com.evan.dokan.data.db.entities.Order
import com.evan.dokan.data.db.entities.Product

interface IOrderUpdateListener {
    fun onUpdate(order: Order)
}