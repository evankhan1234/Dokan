package com.evan.dokan.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.evan.dokan.data.network.post.*
import com.evan.dokan.data.repositories.HomeRepository
import com.evan.dokan.ui.home.cart.ICartCountListener
import com.evan.dokan.ui.home.cart.ICartListDeleteListener
import com.evan.dokan.ui.home.cart.ICartListListener
import com.evan.dokan.ui.home.dashboard.category.ICategoryListListener
import com.evan.dokan.ui.home.dashboard.product.details.ICreateCartListener
import com.evan.dokan.ui.home.dashboard.product.details.IProductLikeListener
import com.evan.dokan.ui.home.order.details.IOrderDetailsListener
import com.evan.dokan.ui.home.settings.IUserListener
import com.evan.dokan.ui.home.settings.profile.IProfileUpdateListener
import com.evan.dokan.ui.home.wishlist.IWishCountListener
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
    var orderIdPost:OrderIdPost?=null
    var userUpdatePost:UserUpdatePost?=null
    var passwordPost:PasswordPost?=null
    var deleteCartPost:DeleteCartPost?=null
    var cartOrderDetailsPost:CartOrderDetailsPost?=null
    var cartListQuantityPost:CartListQuantityPost?=null
    var cartCreatePost:CartCreatePost?=null
    var productLikePost:ProductLikePost?=null
    var wishListCreatePost:WishListCreatePost?=null
    var wishListDeletedPost:WishListDeletedPost?=null
    var categoryListListener: ICategoryListListener?=null
    var productLikeListener: IProductLikeListener?=null
    var wishListCreateListener: IWishListCreateListener?=null
    var wishDeleteListener: IWishDeleteListener?=null
    var wishListListener: IWishListListener?=null
    var createCartListener: ICreateCartListener?=null
    var cartListListener: ICartListListener?=null
    var orderDetailsListener: IOrderDetailsListener?=null
    var cartListDeleteListener: ICartListDeleteListener?=null
    var wishCountListener: IWishCountListener?=null
    var cartCountListener: ICartCountListener?=null
    var userListener: IUserListener?=null
    var profileUpdateListener: IProfileUpdateListener?=null
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
                wishDeleteListener?.onFailure(e?.message!!)
            } catch (e: NoInternetException) {
                wishDeleteListener?.onEnd()
                wishDeleteListener?.onFailure(e?.message!!)
            }
        }

    }
    fun deleteCartList(token:String,shopUserId:Int,productId:Int) {
        cartListDeleteListener?.onStarted()
        Coroutines.main {
            try {
                deleteCartPost= DeleteCartPost(shopUserId,productId)
                Log.e("createWishList", "createWishList" + Gson().toJson(wishListDeletedPost))
                val authResponse = repository.deleteCartList(token!!,deleteCartPost!!)
                cartListDeleteListener?.onSuccess(authResponse?.message!!)
                cartListDeleteListener?.onEnd()
                Log.e("response", "response" + Gson().toJson(authResponse))
            } catch (e: ApiException) {
                cartListDeleteListener?.onEnd()
                cartListDeleteListener?.onFailure(e?.message!!)
            } catch (e: NoInternetException) {
                cartListDeleteListener?.onEnd()
                cartListDeleteListener?.onFailure(e?.message!!)
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
    fun getCartList(header:String,shopUserId:Int) {
        cartListListener?.onStarted()
        Coroutines.main {
            try {
                shopUserIdPost= ShopUserIdPost(shopUserId!!)
                Log.e("Search", "Search" + Gson().toJson(shopUserIdPost))
                val response = repository.getCartList(header,shopUserIdPost!!)
                Log.e("Search", "Search" + Gson().toJson(response))
                cartListListener?.cart(response?.data!!)
                cartListListener?.onEnd()
                Log.e("Search", "Search" + Gson().toJson(response))

            } catch (e: ApiException) {
                cartListListener?.onEnd()
            } catch (e: NoInternetException) {
                cartListListener?.onEnd()
            }
        }

    }
    fun updateCartQuantity(header:String,shopUserId:Int,productId:Int,quantity:Int) {
        Coroutines.main {
            try {
                cartListQuantityPost= CartListQuantityPost(shopUserId,productId,quantity)
                Log.e("response", "response" + Gson().toJson(cartListQuantityPost))
                val response = repository.updateCartQuantity(header,cartListQuantityPost!!)
                Log.e("response", "response" + Gson().toJson(response))
            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun postOrderList(header:String,customerOrderPost: CustomerOrderPost) {
        Coroutines.main {
            try {

                Log.e("response", "response" + Gson().toJson(customerOrderPost))
                val response = repository.postOrderList(header,customerOrderPost!!)
                Log.e("response", "response" + Gson().toJson(response))
            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun updateCartOrderDetails(header:String,data: MutableList<CartOrderPost>?) {
        Coroutines.main {
            try {
                cartOrderDetailsPost= CartOrderDetailsPost(data)
                Log.e("response", "response" + Gson().toJson(cartOrderDetailsPost))
                val response = repository.updateCartOrderDetails(header,cartOrderDetailsPost!!)
                Log.e("response", "response" + Gson().toJson(response))
            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun sendPush(header:String,pushPost: PushPost) {
        Coroutines.main {
            try {
                Log.e("response", "response" + Gson().toJson(pushPost))
                val response = repository.sendPush(header,pushPost!!)
                Log.e("response", "response" + Gson().toJson(response))
            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }

    fun createCart(header:String,productName:String,price:Double,quantity:Int,productId:Int,status:Int,shopUserId:Int,picture:String,created:String) {
        createCartListener?.onStarted()
        Coroutines.main {
            try {
                cartCreatePost= CartCreatePost(productName,price,quantity,productId,status,shopUserId,picture,created)
                Log.e("Search", "Search" + Gson().toJson(cartCreatePost))
                val response = repository.createCart(header,cartCreatePost!!)
                Log.e("Search", "Search" + Gson().toJson(response))
                createCartListener?.onSuccessCart(response?.message!!)
                createCartListener?.onEnd()
                Log.e("Search", "Search" + Gson().toJson(response))

            } catch (e: ApiException) {
                createCartListener?.onEnd()
                createCartListener?.onFailure(e?.message!!)
            } catch (e: NoInternetException) {
                createCartListener?.onEnd()
                createCartListener?.onFailure(e?.message!!)
            }
        }

    }
    fun countWishList(header:String,shopUserId:Int) {
        Coroutines.main {
            try {
                shopUserIdPost= ShopUserIdPost(shopUserId)
                Log.e("response", "response" + Gson().toJson(shopUserIdPost))
                val response = repository.countWishList(header,shopUserIdPost!!)
                wishCountListener?.onWishCount(response?.count!!)
                Log.e("response", "response" + Gson().toJson(response))
            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun countCartList(header:String,shopUserId:Int) {
        Coroutines.main {
            try {
                shopUserIdPost= ShopUserIdPost(shopUserId)
                Log.e("response", "response" + Gson().toJson(shopUserIdPost))
                val response = repository.countCartList(header,shopUserIdPost!!)
                Log.e("response", "response" + Gson().toJson(response))
                cartCountListener?.onCartCount(response?.count!!)
            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun getCustomerDetailsList(header:String,orderId:Int) {
        orderDetailsListener?.onStarted()
        Coroutines.main {
            try {
                orderIdPost= OrderIdPost(orderId!!)
                Log.e("Search", "Search" + Gson().toJson(orderIdPost))
                val response = repository.getCustomerDetailsList(header,orderIdPost!!)
                Log.e("Search", "Search" + Gson().toJson(response))
                orderDetailsListener?.order(response?.data!!)
                orderDetailsListener?.onEnd()
                Log.e("Search", "Search" + Gson().toJson(response))

            } catch (e: ApiException) {
                orderDetailsListener?.onEnd()
            } catch (e: NoInternetException) {
                orderDetailsListener?.onEnd()
            }
        }

    }

    fun getCustomerUser(header:String) {
        userListener?.onStarted()
        Coroutines.main {
            try {
                val response = repository.getCustomerUser(header)
                Log.e("response", "response" + Gson().toJson(response))
                userListener?.onUser(response?.data!!)
                userListener?.onEnd()
            } catch (e: ApiException) {
                userListener?.onEnd()
            } catch (e: NoInternetException) {
                userListener?.onEnd()
            }
        }

    }
    fun updateUserDetails(header:String,id:Int,name:String,address:String,picture:String,gender:Int) {
        profileUpdateListener?.onStarted()
        Coroutines.main {
            try {
                userUpdatePost= UserUpdatePost(id!!,name!!,address!!,picture!!,gender)
                Log.e("Search", "Search" + Gson().toJson(userUpdatePost))
                val response = repository.updateUserDetails(header,userUpdatePost!!)
                Log.e("response", "response" + Gson().toJson(response))
                profileUpdateListener?.onUser(response?.message!!)
                profileUpdateListener?.onEnd()

            } catch (e: ApiException) {
                profileUpdateListener?.onEnd()
                profileUpdateListener?.onFailure(e?.message!!)
            } catch (e: NoInternetException) {
                profileUpdateListener?.onEnd()
                profileUpdateListener?.onFailure(e?.message!!)
            }
        }

    }
    fun updatePassword(header:String,id:Int,password:String) {
        profileUpdateListener?.onStarted()
        Coroutines.main {
            try {
                passwordPost= PasswordPost(id!!,password!!)
                Log.e("Search", "Search" + Gson().toJson(passwordPost))
                val response = repository.updatePassword(header,passwordPost!!)
                Log.e("response", "response" + Gson().toJson(response))
                profileUpdateListener?.onUser(response?.message!!)
                profileUpdateListener?.onEnd()

            } catch (e: ApiException) {
                profileUpdateListener?.onEnd()
                profileUpdateListener?.onFailure(e?.message!!)
            } catch (e: NoInternetException) {
                profileUpdateListener?.onEnd()
                profileUpdateListener?.onFailure(e?.message!!)
            }
        }

    }


}