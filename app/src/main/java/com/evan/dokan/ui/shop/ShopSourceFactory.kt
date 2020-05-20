package com.evan.dokan.ui.shop

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.evan.dokan.data.db.entities.Shop

class ShopSourceFactory (private var dataSource: ShopDataSource) :
    DataSource.Factory<Int, Shop>() {

    val mutableLiveData: MutableLiveData<ShopDataSource> = MutableLiveData()
    override fun create(): DataSource<Int, Shop> {
        mutableLiveData.postValue(dataSource)
        return dataSource
    }
}