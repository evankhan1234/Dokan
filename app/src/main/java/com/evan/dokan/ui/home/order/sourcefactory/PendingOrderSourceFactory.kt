package com.evan.dokan.ui.home.order.sourcefactory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.evan.dokan.data.db.entities.Order
import com.evan.dokan.data.db.entities.Shop
import com.evan.dokan.ui.home.order.source.PendingOrderDataSource
import com.evan.dokan.ui.home.order.source.ProcessingDataSource
import com.evan.dokan.ui.shop.ShopDataSource

class PendingOrderSourceFactory (private var dataSource: PendingOrderDataSource) :
    DataSource.Factory<Int, Order>() {

    val mutableLiveData: MutableLiveData<PendingOrderDataSource> = MutableLiveData()
    override fun create(): DataSource<Int, Order> {
        mutableLiveData.postValue(dataSource)
        return dataSource
    }
}