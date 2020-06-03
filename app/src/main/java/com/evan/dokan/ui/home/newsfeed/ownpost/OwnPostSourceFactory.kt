package com.evan.dokan.ui.home.newsfeed.ownpost

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.evan.dokan.data.db.entities.Post
import com.evan.dokan.data.db.entities.Shop
import com.evan.dokan.ui.shop.ShopDataSource

class OwnPostSourceFactory  (private var dataSource: OwnPostDataSource) :
    DataSource.Factory<Int, Post>() {

    val mutableLiveData: MutableLiveData<OwnPostDataSource> = MutableLiveData()
    override fun create(): DataSource<Int, Post> {
        mutableLiveData.postValue(dataSource)
        return dataSource
    }
}