package com.evan.dokan.ui.home.dashboard.product

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.evan.dokan.data.db.entities.Product
import com.evan.dokan.data.db.entities.Shop
import com.evan.dokan.ui.shop.ShopDataSource

class ProductCategoryWiseSourceFactory (private var dataSource: ProductCategoryWiseDataSource) :
    DataSource.Factory<Int, Product>() {

    val mutableLiveData: MutableLiveData<ProductCategoryWiseDataSource> = MutableLiveData()
    override fun create(): DataSource<Int, Product> {
        mutableLiveData.postValue(dataSource)
        return dataSource
    }
}