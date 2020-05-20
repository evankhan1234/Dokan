package com.evan.dokan.ui.shop

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.evan.dokan.data.db.entities.Shop
import com.evan.dokan.data.network.post.LimitPost
import com.evan.dokan.data.network.post.ShopPost
import com.evan.dokan.data.repositories.HomeRepository
import com.evan.dokan.util.*

class ShopDataSource (val context: Context, val alertRepository: HomeRepository) :
    PageKeyedDataSource<Int, Shop>() {

    var networkState: MutableLiveData<NetworkState> = MutableLiveData()
    var post: ShopPost? = null
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Shop>
    ) {
        Coroutines.main {
            networkState.postValue(NetworkState.DONE)

            try {
                networkState.postValue(NetworkState.LOADING)
                post = ShopPost(SharedPreferenceUtil.getShared(context, SharedPreferenceUtil.TYPE_LATITUDE)!!,SharedPreferenceUtil.getShared(context, SharedPreferenceUtil.TYPE_LONGITUDE)!!,10, 1)
                val response = alertRepository.getShopPagination(SharedPreferenceUtil.getShared(context, SharedPreferenceUtil.TYPE_AUTH_TOKEN)!!,post!!)
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

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Shop>) {
        Coroutines.main {
            try {
                networkState.postValue(NetworkState.LOADING)
                post = ShopPost(SharedPreferenceUtil.getShared(context, SharedPreferenceUtil.TYPE_LATITUDE)!!,SharedPreferenceUtil.getShared(context, SharedPreferenceUtil.TYPE_LONGITUDE)!!,10, params.key)
                val response =
                    alertRepository.getShopPagination(SharedPreferenceUtil.getShared(context, SharedPreferenceUtil.TYPE_AUTH_TOKEN)!!,post!!)
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

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Shop>) {
    }


}