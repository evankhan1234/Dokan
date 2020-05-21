package com.evan.dokan.ui.home.dashboard.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evan.dokan.data.repositories.HomeRepository
import com.evan.dokan.ui.shop.ShopSourceFactory
import com.evan.dokan.ui.shop.ShopViewModel

class ProductCategoryWiseModelFactory (
    private val repository: HomeRepository, private val sourceFactory: ProductCategoryWiseSourceFactory
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProductCategoryWiseViewModel(repository,sourceFactory) as T
    }
}