package com.evan.dokan.ui.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evan.dokan.data.repositories.HomeRepository

class ShopModelFactory (
    private val repository: HomeRepository, private val sourceFactory: ShopSourceFactory
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShopViewModel(repository,sourceFactory) as T
    }
}