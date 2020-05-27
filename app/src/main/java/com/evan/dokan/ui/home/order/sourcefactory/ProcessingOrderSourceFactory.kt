package com.evan.dokan.ui.home.order.sourcefactory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.evan.dokan.data.db.entities.Order
import com.evan.dokan.data.db.entities.Shop
import com.evan.dokan.ui.home.order.source.ProcessingDataSource
import com.evan.dokan.ui.shop.ShopDataSource

class ProcessingOrderSourceFactory (private var dataSource: ProcessingDataSource) :
    DataSource.Factory<Int, Order>() {

    val mutableLiveData: MutableLiveData<ProcessingDataSource> = MutableLiveData()
    override fun create(): DataSource<Int, Order> {
        mutableLiveData.postValue(dataSource)
        return dataSource
    }
}