package com.evan.dokan.ui.home.dashboard.search

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.evan.dokan.data.db.entities.Product
import com.evan.dokan.data.network.post.CustomerProductSearchPost
import com.evan.dokan.data.network.post.ProductSearchPost
import com.evan.dokan.data.repositories.HomeRepository
import com.evan.dokan.ui.home.dashboard.product.IProductCategoryWiseListener
import com.evan.dokan.ui.home.dashboard.product.ProductCategoryWiseDataSource
import com.evan.dokan.ui.home.dashboard.product.ProductCategoryWiseSourceFactory
import com.evan.dokan.util.ApiException
import com.evan.dokan.util.Coroutines
import com.evan.dokan.util.NetworkState
import com.evan.dokan.util.NoInternetException
import com.google.gson.Gson

class ProductSearchViewModel (
    val repository: HomeRepository,
    val alertListSourceFactory: ProductSearchSourceFactory
) : ViewModel() {


    var productCategoryWiseListener: IProductCategoryWiseListener?=null
    var searchPost: CustomerProductSearchPost?=null
    var listOfAlerts: LiveData<PagedList<Product>>? = null
    private val pageSize = 7

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(true)
            .build()
        listOfAlerts = LivePagedListBuilder<Int, Product>(alertListSourceFactory, config).build()
    }

    fun replaceSubscription(lifecycleOwner: LifecycleOwner) {
        listOfAlerts?.removeObservers(lifecycleOwner)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(false)
            .build()
        listOfAlerts =
            LivePagedListBuilder<Int, Product>(alertListSourceFactory, config).build()
    }

    fun getNetworkState(): LiveData<NetworkState> =
        Transformations.switchMap<ProductSearchDataSource, NetworkState>(
            alertListSourceFactory.mutableLiveData,
            ProductSearchDataSource::networkState
        )

    fun getSearch(header:String,keyword:String,shopUserId:String) {
        productCategoryWiseListener?.started()
        Coroutines.main {
            try {
                searchPost= CustomerProductSearchPost(shopUserId,keyword)
                Log.e("Search", "Search" + Gson().toJson(searchPost))
                val response = repository.getProductSearchByCustomer(header,searchPost!!)
                Log.e("Search", "Search" + Gson().toJson(response))
                productCategoryWiseListener?.show(response?.data!!)
                productCategoryWiseListener?.end()
                Log.e("Search", "Search" + Gson().toJson(response))

            } catch (e: ApiException) {
                productCategoryWiseListener?.end()
                productCategoryWiseListener?.exit()
            } catch (e: NoInternetException) {
                productCategoryWiseListener?.end()
            }
        }

    }
}