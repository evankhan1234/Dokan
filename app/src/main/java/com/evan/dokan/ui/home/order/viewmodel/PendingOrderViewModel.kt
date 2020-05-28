package com.evan.dokan.ui.home.order.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.evan.dokan.data.db.entities.Order
import com.evan.dokan.data.network.post.SearchPost
import com.evan.dokan.data.repositories.HomeRepository
import com.evan.dokan.ui.home.order.source.DeliveredDataSource
import com.evan.dokan.ui.home.order.source.PendingOrderDataSource
import com.evan.dokan.ui.home.order.sourcefactory.DeliveredOrderSourceFactory
import com.evan.dokan.ui.home.order.sourcefactory.PendingOrderSourceFactory
import com.evan.dokan.ui.shop.IShopListener
import com.evan.dokan.util.NetworkState

class PendingOrderViewModel (
    val repository: HomeRepository,
    val alertListSourceFactory: PendingOrderSourceFactory
) : ViewModel() {


    var shopListener: IShopListener?=null
    var searchPost: SearchPost?=null
    var listOfAlerts: LiveData<PagedList<Order>>? = null
    private val pageSize = 7

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(true)
            .build()
        listOfAlerts = LivePagedListBuilder<Int, Order>(alertListSourceFactory, config).build()
    }

    fun replaceSubscription(lifecycleOwner: LifecycleOwner) {
        listOfAlerts?.removeObservers(lifecycleOwner)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(false)
            .build()
        listOfAlerts =
            LivePagedListBuilder<Int, Order>(alertListSourceFactory, config).build()
    }

    fun getNetworkState(): LiveData<NetworkState> =
        Transformations.switchMap<PendingOrderDataSource, NetworkState>(
            alertListSourceFactory.mutableLiveData,
            PendingOrderDataSource::networkState
        )


}