package com.evan.dokan.ui.home.dashboard.search

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.evan.dokan.data.db.entities.Product
import com.evan.dokan.ui.home.dashboard.product.ProductCategoryWiseDataSource

class ProductSearchSourceFactory (private var dataSource: ProductSearchDataSource) :
    DataSource.Factory<Int, Product>() {

    val mutableLiveData: MutableLiveData<ProductSearchDataSource> = MutableLiveData()
    override fun create(): DataSource<Int, Product> {
        mutableLiveData.postValue(dataSource)
        return dataSource
    }
}