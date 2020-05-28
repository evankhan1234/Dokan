package com.evan.dokan.ui.home.dashboard.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evan.dokan.data.repositories.HomeRepository
import com.evan.dokan.ui.home.dashboard.product.ProductCategoryWiseSourceFactory
import com.evan.dokan.ui.home.dashboard.product.ProductCategoryWiseViewModel

class ProductSearchModelFactory (
    private val repository: HomeRepository, private val sourceFactory: ProductSearchSourceFactory
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProductSearchViewModel(repository,sourceFactory) as T
    }
}