package com.evan.dokan.ui.home.order.modelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evan.dokan.data.repositories.HomeRepository
import com.evan.dokan.ui.home.order.sourcefactory.DeliveredOrderSourceFactory
import com.evan.dokan.ui.home.order.viewmodel.DeliveredOrderViewModel
import com.evan.dokan.ui.shop.ShopSourceFactory
import com.evan.dokan.ui.shop.ShopViewModel

class DeliveredOrderModelFactory (
    private val repository: HomeRepository, private val sourceFactory: DeliveredOrderSourceFactory
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DeliveredOrderViewModel(repository,sourceFactory) as T
    }
}