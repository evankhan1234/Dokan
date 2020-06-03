package com.evan.dokan.ui.home.newsfeed.ownpost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evan.dokan.data.repositories.HomeRepository
import com.evan.dokan.ui.shop.ShopSourceFactory
import com.evan.dokan.ui.shop.ShopViewModel

class OwnPostModelFactory (
    private val repository: HomeRepository, private val sourceFactory: OwnPostSourceFactory
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OwnPostViewModel(repository,sourceFactory) as T
    }
}