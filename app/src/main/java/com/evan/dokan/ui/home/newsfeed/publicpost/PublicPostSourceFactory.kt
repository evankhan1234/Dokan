package com.evan.dokan.ui.home.newsfeed.publicpost

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.evan.dokan.data.db.entities.Post
import com.evan.dokan.data.db.entities.Shop
import com.evan.dokan.ui.shop.ShopDataSource

class PublicPostSourceFactory  (private var dataSource: PublicPostDataSource) :
    DataSource.Factory<Int, Post>() {

    val mutableLiveData: MutableLiveData<PublicPostDataSource> = MutableLiveData()
    override fun create(): DataSource<Int, Post> {
        mutableLiveData.postValue(dataSource)
        return dataSource
    }
}