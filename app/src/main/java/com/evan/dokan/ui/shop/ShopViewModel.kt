package com.evan.dokan.ui.shop

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.evan.dokan.data.db.entities.Shop
import com.evan.dokan.data.network.post.SearchPost
import com.evan.dokan.data.repositories.HomeRepository
import com.evan.dokan.util.ApiException
import com.evan.dokan.util.Coroutines
import com.evan.dokan.util.NetworkState
import com.evan.dokan.util.NoInternetException
import com.google.gson.Gson

class ShopViewModel (
    val repository: HomeRepository,
    val alertListSourceFactory: ShopSourceFactory
) : ViewModel() {


    var shopListener:IShopListener?=null
    var searchPost:SearchPost?=null
    var listOfAlerts: LiveData<PagedList<Shop>>? = null
    private val pageSize = 7

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(true)
            .build()
        listOfAlerts = LivePagedListBuilder<Int, Shop>(alertListSourceFactory, config).build()
    }

    fun replaceSubscription(lifecycleOwner: LifecycleOwner) {
        listOfAlerts?.removeObservers(lifecycleOwner)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(false)
            .build()
        listOfAlerts =
            LivePagedListBuilder<Int, Shop>(alertListSourceFactory, config).build()
    }

    fun getNetworkState(): LiveData<NetworkState> =
        Transformations.switchMap<ShopDataSource, NetworkState>(
            alertListSourceFactory.mutableLiveData,
            ShopDataSource::networkState
        )

    fun getSearch(header:String,keyword:String) {
        shopListener?.started()
        Coroutines.main {
            try {
                searchPost= SearchPost(keyword)
                val response = repository.getShopSearch(header,searchPost!!)
                shopListener?.show(response?.data!!)
                shopListener?.end()
                Log.e("Search", "Search" + Gson().toJson(response))

            } catch (e: ApiException) {
                shopListener?.end()
                shopListener?.exit()
            } catch (e: NoInternetException) {
                shopListener?.end()
            }
        }

    }
}