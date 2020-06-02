package com.evan.dokan.ui.home.newsfeed.ownpost

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.evan.dokan.data.db.entities.Post
import com.evan.dokan.data.repositories.HomeRepository
import com.evan.dokan.util.NetworkState

class OwnPostViewModel (
    val repository: HomeRepository,
    val alertListSourceFactory: OwnPostSourceFactory
) : ViewModel() {

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
        Transformations.switchMap<OwnPostDataSource, NetworkState>(
            alertListSourceFactory.mutableLiveData,
            OwnPostDataSource::networkState
        )

}