package com.evan.dokan.ui.home.newsfeed.publicpost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evan.dokan.data.repositories.HomeRepository
import com.evan.dokan.ui.shop.ShopSourceFactory
import com.evan.dokan.ui.shop.ShopViewModel

class PublicPostModelFactory (
    private val repository: HomeRepository, private val sourceFactory: PublicPostSourceFactory
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PublicPostViewModel(repository,sourceFactory) as T
    }
}