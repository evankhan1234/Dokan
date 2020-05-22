package com.evan.dokan.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.evan.dokan.data.network.post.ProductLikePost
import com.evan.dokan.data.network.post.ShopUserIdPost
import com.evan.dokan.data.network.post.WishListCreatePost
import com.evan.dokan.data.network.post.WishListDeletedPost
import com.evan.dokan.data.repositories.HomeRepository
import com.evan.dokan.ui.home.dashboard.category.ICategoryListListener
import com.evan.dokan.ui.home.dashboard.product.details.IProductLikeListener
import com.evan.dokan.ui.home.wishlist.IWishDeleteListener
import com.evan.dokan.ui.home.wishlist.IWishListCreateListener
import com.evan.dokan.ui.home.wishlist.IWishListListener
import com.evan.dokan.util.ApiException
import com.evan.dokan.util.Coroutines
import com.evan.dokan.util.NoInternetException
import com.google.gson.Gson

class HomeViewModel (
    private val repository: HomeRepository
) : ViewModel() {

    var shopUserIdPost:ShopUserIdPost?=null
    var productLikePost:ProductLikePost?=null
    var wishListCreatePost:WishListCreatePost?=null
    var wishListDeletedPost:WishListDeletedPost?=null
    var categoryListListener: ICategoryListListener?=null
    var productLikeListener: IProductLikeListener?=null
    var wishListCreateListener: IWishListCreateListener?=null
    var wishDeleteListener: IWishDeleteListener?=null
    var wishListListener: IWishListListener?=null
    fun getCategory(token:String,shopUserId:Int) {
        categoryListListener?.onStarted()
        Coroutines.main {
            try {
                shopUserIdPost=ShopUserIdPost(shopUserId)
                val authResponse = repository.getCategory(token!!,shopUserIdPost!!)
                categoryListListener?.category(authResponse?.data!!)
                Log.e("response", "response" + Gson().toJson(authResponse))
                categoryListListener?.onEnd()
            } catch (e: ApiException) {
                categoryListListener?.onEnd()
            } catch (e: NoInternetException) {
                categoryListListener?.onEnd()
            }
        }

    }

    fun getProductLike(token:String,productId:Int,shopUserId:Int) {

        Coroutines.main {
            try {
                productLikePost=ProductLikePost(shopUserId,productId)
                Log.e("productLikePost", "productLikePost" + Gson().toJson(productLikePost))
                val authResponse = repository.getProductLike(token!!,productLikePost!!)
                productLikeListener?.onBoolean(authResponse?.success!!)
                Log.e("response", "response" + Gson().toJson(authResponse))
            } catch (e: ApiException) {
            } catch (e: NoInternetException) {
            }
        }

    }
    fun createWishList(token:String,shopUserId:Int,productId:Int,status:Int,created:String) {
        wishListCreateListener?.onStarted()
        Coroutines.main {
            try {
                wishListCreatePost=WishListCreatePost(shopUserId,productId,status,created)
                Log.e("createWishList", "createWishList" + Gson().toJson(wishListCreatePost))
                val authResponse = repository.createWishList(token!!,wishListCreatePost!!)
                wishListCreateListener?.onSuccess(authResponse?.message!!)
                wishListCreateListener?.onEnd()
                Log.e("response", "response" + Gson().toJson(authResponse))
            } catch (e: ApiException) {
                wishListCreateListener?.onEnd()
                wishListCreateListener?.onSuccess(e?.message!!)
            } catch (e: NoInternetException) {
                wishListCreateListener?.onEnd()
                wishListCreateListener?.onSuccess(e?.message!!)
            }
        }

    }
    fun deleteWishList(token:String,shopUserId:Int,productId:Int) {
        wishDeleteListener?.onStarted()
        Coroutines.main {
            try {
                wishListDeletedPost=WishListDeletedPost(shopUserId,productId)
                Log.e("createWishList", "createWishList" + Gson().toJson(wishListDeletedPost))
                val authResponse = repository.deleteWishList(token!!,wishListDeletedPost!!)
                wishDeleteListener?.onSuccess(authResponse?.message!!)
                wishDeleteListener?.onEnd()
                Log.e("response", "response" + Gson().toJson(authResponse))
            } catch (e: ApiException) {
                wishDeleteListener?.onEnd()
                wishDeleteListener?.onSuccess(e?.message!!)
            } catch (e: NoInternetException) {
                wishDeleteListener?.onEnd()
                wishDeleteListener?.onSuccess(e?.message!!)
            }
        }

    }
    fun getWishList(header:String,shopUserId:Int) {
        wishListListener?.onStarted()
        Coroutines.main {
            try {
                shopUserIdPost= ShopUserIdPost(shopUserId!!)
                Log.e("Search", "Search" + Gson().toJson(shopUserIdPost))
                val response = repository.getWishList(header,shopUserIdPost!!)
                Log.e("Search", "Search" + Gson().toJson(response))
                wishListListener?.wish(response?.data!!)
                wishListListener?.onEnd()
                Log.e("Search", "Search" + Gson().toJson(response))

            } catch (e: ApiException) {
                wishListListener?.onEnd()
            } catch (e: NoInternetException) {
                wishListListener?.onEnd()
            }
        }

    }

}