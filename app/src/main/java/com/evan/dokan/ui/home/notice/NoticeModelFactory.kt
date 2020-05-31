package com.evan.dokan.ui.home.notice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evan.dokan.data.repositories.HomeRepository
import com.evan.dokan.ui.shop.ShopSourceFactory
import com.evan.dokan.ui.shop.ShopViewModel

class NoticeModelFactory (
    private val repository: HomeRepository, private val sourceFactory: NoticeSourceFactory
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NoticeViewModel(repository,sourceFactory) as T
    }
}