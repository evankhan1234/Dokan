package com.evan.dokan.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.evan.dokan.data.network.post.ProductLikePost
import com.evan.dokan.data.network.post.ShopUserIdPost
import com.evan.dokan.data.repositories.HomeRepository
import com.evan.dokan.ui.home.dashboard.category.ICategoryListListener
import com.evan.dokan.ui.home.dashboard.product.details.IProductLikeListener
import com.evan.dokan.util.ApiException
import com.evan.dokan.util.Coroutines
import com.evan.dokan.util.NoInternetException
import com.google.gson.Gson

class HomeViewModel (
    private val repository: HomeRepository
) : ViewModel() {

    var shopUserIdPost:ShopUserIdPost?=null
    var productLikePost:ProductLikePost?=null
    var categoryListListener: ICategoryListListener?=null
    var productLikeListener: IProductLikeListener?=null
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


}