package com.evan.dokan

import android.app.Application
import com.evan.dokan.data.db.AppDatabase
import com.evan.dokan.data.network.MyApi
import com.evan.dokan.data.network.NetworkConnectionInterceptor
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
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from singleton { HomeRepository(instance()) }
        bind() from singleton { QuotesRepository(instance(), instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { ShopModelFactory(instance(),instance()) }
        bind() from provider { ProductCategoryWiseModelFactory(instance(),instance()) }
        bind() from provider { ShopSourceFactory(instance()) }
        bind() from provider { ProductCategoryWiseSourceFactory(instance()) }
        bind() from provider { ShopViewModel(instance(),instance()) }
        bind() from provider { ProductCategoryWiseViewModel(instance(),instance()) }
        bind() from provider { ShopDataSource(instance(),instance()) }
        bind() from provider { ProductCategoryWiseDataSource(instance(),instance()) }
        bind() from provider { ProfileViewModelFactory(instance()) }
        bind() from provider { HomeViewModelFactory(instance()) }
        bind() from provider { HomeViewModel(instance()) }
        bind() from provider{ QuotesViewModelFactory(instance()) }


    }

}