package com.evan.dokan

import android.app.Application
import com.evan.dokan.data.db.AppDatabase
import com.evan.dokan.data.network.MyApi
import com.evan.dokan.data.network.NetworkConnectionInterceptor
import com.evan.dokan.data.network.PushApi
import com.evan.dokan.data.preferences.PreferenceProvider
import com.evan.dokan.data.repositories.HomeRepository
import com.evan.dokan.data.repositories.QuotesRepository
import com.evan.dokan.data.repositories.UserRepository
import com.evan.dokan.ui.auth.AuthViewModelFactory
import com.evan.dokan.ui.home.HomeViewModel
import com.evan.dokan.ui.home.HomeViewModelFactory
import com.evan.dokan.ui.home.dashboard.product.ProductCategoryWiseDataSource
import com.evan.dokan.ui.home.dashboard.product.ProductCategoryWiseModelFactory
import com.evan.dokan.ui.home.dashboard.product.ProductCategoryWiseSourceFactory
import com.evan.dokan.ui.home.dashboard.product.ProductCategoryWiseViewModel
import com.evan.dokan.ui.home.dashboard.search.ProductSearchDataSource
import com.evan.dokan.ui.home.dashboard.search.ProductSearchModelFactory
import com.evan.dokan.ui.home.dashboard.search.ProductSearchSourceFactory
import com.evan.dokan.ui.home.dashboard.search.ProductSearchViewModel
import com.evan.dokan.ui.home.order.modelfactory.DeliveredOrderModelFactory
import com.evan.dokan.ui.home.order.modelfactory.PendingOrderModelFactory
import com.evan.dokan.ui.home.order.modelfactory.ProcessingOrderModelFactory
import com.evan.dokan.ui.home.order.source.DeliveredDataSource
import com.evan.dokan.ui.home.order.source.PendingOrderDataSource
import com.evan.dokan.ui.home.order.source.ProcessingDataSource
import com.evan.dokan.ui.home.order.sourcefactory.DeliveredOrderSourceFactory
import com.evan.dokan.ui.home.order.sourcefactory.PendingOrderSourceFactory
import com.evan.dokan.ui.home.order.sourcefactory.ProcessingOrderSourceFactory
import com.evan.dokan.ui.home.order.viewmodel.DeliveredOrderViewModel
import com.evan.dokan.ui.home.order.viewmodel.PendingOrderViewModel
import com.evan.dokan.ui.home.order.viewmodel.ProcessingOrderViewModel
import com.evan.dokan.ui.home.profile.ProfileViewModelFactory
import com.evan.dokan.ui.home.quotes.QuotesViewModelFactory
import com.evan.dokan.ui.shop.ShopDataSource
import com.evan.dokan.ui.shop.ShopModelFactory
import com.evan.dokan.ui.shop.ShopSourceFactory
import com.evan.dokan.ui.shop.ShopViewModel

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MVVMApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { PushApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from singleton { HomeRepository(instance(),instance()) }
        bind() from singleton { QuotesRepository(instance(), instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { ShopModelFactory(instance(),instance()) }
        bind() from provider { ProductCategoryWiseModelFactory(instance(),instance()) }
        bind() from provider { DeliveredOrderModelFactory(instance(),instance()) }
        bind() from provider { ProcessingOrderModelFactory(instance(),instance()) }
        bind() from provider { PendingOrderModelFactory(instance(),instance()) }
        bind() from provider { ShopSourceFactory(instance()) }
        bind() from provider { DeliveredOrderSourceFactory(instance()) }
        bind() from provider { ProcessingOrderSourceFactory(instance()) }
        bind() from provider { PendingOrderSourceFactory(instance()) }
        bind() from provider { ProductCategoryWiseSourceFactory(instance()) }
        bind() from provider { ShopViewModel(instance(),instance()) }
        bind() from provider { ProductSearchModelFactory(instance(),instance()) }
        bind() from provider { ProductSearchSourceFactory(instance()) }
        bind() from provider { ProductSearchViewModel(instance(),instance()) }
        bind() from provider { ProductSearchDataSource(instance(),instance()) }
        bind() from provider { DeliveredOrderViewModel(instance(),instance()) }
        bind() from provider { ProcessingOrderViewModel(instance(),instance()) }
        bind() from provider { PendingOrderViewModel(instance(),instance()) }
        bind() from provider { ProductCategoryWiseViewModel(instance(),instance()) }
        bind() from provider { ShopDataSource(instance(),instance()) }
        bind() from provider { DeliveredDataSource(instance(),instance()) }
        bind() from provider { ProcessingDataSource(instance(),instance()) }
        bind() from provider { PendingOrderDataSource(instance(),instance()) }
        bind() from provider { ProductCategoryWiseDataSource(instance(),instance()) }
        bind() from provider { ProfileViewModelFactory(instance()) }
        bind() from provider { HomeViewModelFactory(instance()) }
        bind() from provider { HomeViewModel(instance()) }
        bind() from provider{ QuotesViewModelFactory(instance()) }


    }

}