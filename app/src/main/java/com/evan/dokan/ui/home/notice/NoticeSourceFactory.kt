package com.evan.dokan.ui.home.notice

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.evan.dokan.data.db.entities.Notice
import com.evan.dokan.data.db.entities.Shop
import com.evan.dokan.ui.shop.ShopDataSource

class NoticeSourceFactory (private var dataSource: NoticeDataSource) :
    DataSource.Factory<Int, Notice>() {

    val mutableLiveData: MutableLiveData<NoticeDataSource> = MutableLiveData()
    override fun create(): DataSource<Int, Notice> {
        mutableLiveData.postValue(dataSource)
        return dataSource
    }
}