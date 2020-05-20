package com.evan.dokan.ui.home.dashboard.product

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.evan.dokan.data.db.entities.Product
import com.evan.dokan.data.network.post.ProductPost
import com.evan.dokan.data.network.post.ProductSearchPost

import com.evan.dokan.data.repositories.HomeRepository
import com.evan.dokan.util.*

class ProductCategoryWiseDataSource (val context: Context, val alertRepository: HomeRepository) :
    PageKeyedDataSource<Int, Product>() {

    var networkState: MutableLiveData<NetworkState> = MutableLiveData()
    var post: ProductPost? = null
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Product>
    ) {
        Coroutines.main {
            networkState.postValue(NetworkState.DONE)

            try {
                networkState.postValue(NetworkState.LOADING)
                post = ProductPost(
                    SharedPreferenceUtil.getShared(context, SharedPreferenceUtil.TYPE_SHOP_ID)!!,
                    SharedPreferenceUtil.getShared(context, SharedPreferenceUtil.TYPE_PRODUCT_CATEGORY)!!,10, 1)
                val response = alertRepository.getProductCategory(SharedPreferenceUtil.getShared(context, SharedPreferenceUtil.TYPE_AUTH_TOKEN)!!,post!!)
                Log.e("response","response"+response)
                response.success.let { isSuccessful ->
                    if (isSuccessful!!) {
                        networkState.postValue(NetworkState.DONE)
                        val nextPageKey = 2
                        callback.onResult(response.data!!, null, nextPageKey)
                        return@main
                    } else {
                        networkState.postValue(
                            NetworkState(
                                NetworkState.Status.FAILED,
                                response?.message!!
                            )
                        )
                    }
                }
            } catch (e: ApiException) {

                networkState.postValue(NetworkState(NetworkState.Status.FAILED, e.localizedMessage))
            } catch (e: NoInternetException) {
                networkState.postValue(NetworkState(NetworkState.Status.FAILED, e.localizedMessage))
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Product>) {
        Coroutines.main {
            try {
                networkState.postValue(NetworkState.LOADING)
                post = ProductPost(
                    SharedPreferenceUtil.getShared(context, SharedPreferenceUtil.TYPE_SHOP_ID)!!,
                    SharedPreferenceUtil.getShared(context, SharedPreferenceUtil.TYPE_PRODUCT_CATEGORY)!!,10, params.key)
                val response =
                    alertRepository.getProductCategory(SharedPreferenceUtil.getShared(context, SharedPreferenceUtil.TYPE_AUTH_TOKEN)!!,post!!)
                response.success.let { isSuccessful ->
                    if (isSuccessful!!) {
                        val nextPageKey =
                            params.key + 1
                        networkState.postValue(NetworkState.DONE)
                        callback.onResult(response.data!!, nextPageKey)
                        return@main
                    } else {
                        networkState.postValue(
                            NetworkState(
                                NetworkState.Status.FAILED,
                                response?.message!!
                            )
                        )

                    }
                }
            } catch (e: ApiException) {
                networkState.postValue(NetworkState(NetworkState.Status.FAILED, e.localizedMessage!!))
            } catch (e: NoInternetException) {
                networkState.postValue(NetworkState(NetworkState.Status.FAILED, e.localizedMessage!!))
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Product>) {
    }


}