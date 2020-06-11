package com.evan.dokan.ui.home.order.details

import com.evan.dokan.data.db.entities.CustomerOrder

interface ICustomerOrderListener {
    fun onShow(customerOrder: CustomerOrder?)
    fun onEmpty()
}