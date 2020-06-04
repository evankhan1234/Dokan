package com.evan.dokan.ui.home.newsfeed.publicpost

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.evan.dokan.data.db.entities.Post
import com.evan.dokan.data.network.post.LikeCountPost
import com.evan.dokan.data.network.post.LovePost

import com.evan.dokan.data.network.post.SearchPost
import com.evan.dokan.data.repositories.HomeRepository
import com.evan.dokan.ui.shop.IShopListener
import com.evan.dokan.ui.shop.ShopDataSource
import com.evan.dokan.ui.shop.ShopSourceFactory
import com.evan.dokan.util.ApiException
import com.evan.dokan.util.Coroutines
import com.evan.dokan.util.NetworkState
import com.evan.dokan.util.NoInternetException
import com.google.gson.Gson

class PublicPostViewModel (
    val repository: HomeRepository,
    val alertListSourceFactory: PublicPostSourceFactory
) : ViewModel() {
    var likeCountPost:LikeCountPost?=null
    var lovePost:LovePost?=null

    var listOfAlerts: LiveData<PagedList<Post>>? = null
    private val pageSize = 7

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(true)
            .build()
        listOfAlerts = LivePagedListBuilder(alertListSourceFactory, config).build()
    }

    fun replaceSubscription(lifecycleOwner: LifecycleOwner) {
        listOfAlerts?.removeObservers(lifecycleOwner)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(false)
            .build()
        listOfAlerts =
            LivePagedListBuilder(alertListSourceFactory, config).build()
    }

    fun getNetworkState(): LiveData<NetworkState> =
        Transformations.switchMap<PublicPostDataSource, NetworkState>(
            alertListSourceFactory.mutableLiveData,
            PublicPostDataSource::networkState
        )
    fun updatedLikeCount(header:String,id: Int,love:Int) {
        Coroutines.main {
            try {
                likeCountPost= LikeCountPost(id,love)
                val response = repository.updatedLikeCount(header,likeCountPost!!)
                Log.e("response", "response" + Gson().toJson(response))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun createdLove(header:String,postId: Int,type:Int) {
        Coroutines.main {
            try {
                lovePost= LovePost(postId,type)
                val response = repository.createdLove(header,lovePost!!)
                Log.e("response", "response" + Gson().toJson(response))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun deletedLove(header:String,postId: Int,type:Int) {
        Coroutines.main {
            try {
                lovePost= LovePost(postId,type)
                val response = repository.deletedLove(header,lovePost!!)
                Log.e("response", "response" + Gson().toJson(response))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }

}