package com.evan.dokan.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.evan.dokan.data.network.post.*
import com.evan.dokan.data.repositories.HomeRepository
import com.evan.dokan.ui.home.cart.ICartCountListener
import com.evan.dokan.ui.home.cart.ICartListDeleteListener
import com.evan.dokan.ui.home.cart.ICartListListener
import com.evan.dokan.ui.home.cart.IPushListener
import com.evan.dokan.ui.home.dashboard.IRecentProductListener
import com.evan.dokan.ui.home.dashboard.category.ICategoryListListener
import com.evan.dokan.ui.home.dashboard.product.details.ICreateCartListener
import com.evan.dokan.ui.home.dashboard.product.details.IProductLikeListener
import com.evan.dokan.ui.home.newsfeed.ownpost.IPostListener
import com.evan.dokan.ui.home.newsfeed.publicpost.comments.ICommentsListener
import com.evan.dokan.ui.home.newsfeed.publicpost.comments.ICommentsPostListener
import com.evan.dokan.ui.home.newsfeed.publicpost.comments.ISucceslistener
import com.evan.dokan.ui.home.newsfeed.publicpost.reply.IReplyListener
import com.evan.dokan.ui.home.newsfeed.publicpost.reply.IReplyPostListener
import com.evan.dokan.ui.home.order.details.ICustomerOrderListener
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
    var commentsPost:CommentsPost?=null
    var replyPost:ReplyPost?=null
    var ownUpdatedPost:OwnUpdatedPost?=null
    var likeCountPost:LikeCountPost?=null
    var commentsForPost:CommentsForPost?=null
    var replyForPost:ReplyForPost?=null

    var orderIdPost:OrderIdPost?=null
    var cutomerOrderPost:CutomerOrderPost?=null
    var tokenPost:TokenPost?=null
    var userUpdatePost:UserUpdatePost?=null
    var passwordPost:PasswordPost?=null
    var newsfeedPost:NewsfeedPost?=null
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
    var recentProductListener: IRecentProductListener?=null
    var postListener: IPostListener?=null
    var commentsPostListener: ICommentsPostListener?=null
    var commentsListener: ICommentsListener?=null
    var succeslistener: ISucceslistener?=null
    var replyListener: IReplyListener?=null
    var replyPostListener: IReplyPostListener?=null
    var customerOrderListener: ICustomerOrderListener?=null
    var pushListener: IPushListener?=null
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
                Log.e("response", "response" + e?.message)
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
    fun getRecentProduct(header:String,shopUserId:Int) {
        recentProductListener?.onStarted()
        Coroutines.main {
            try {
                shopUserIdPost= ShopUserIdPost(shopUserId)
                Log.e("Search", "Search" + Gson().toJson(shopUserIdPost))
                val response = repository.getRecentProduct(header,shopUserIdPost!!)
                Log.e("Search", "Search" + Gson().toJson(response))
                recentProductListener?.product(response?.data!!)
                recentProductListener?.onEnd()
                Log.e("Search", "Search" + Gson().toJson(response))

            } catch (e: ApiException) {
                recentProductListener?.onEnd()
            } catch (e: NoInternetException) {
                recentProductListener?.onEnd()
            }
        }

    }

    fun createdNewsFeedPost(header:String,Name:String,content:String,picture:String,created:String,status:Int,type:Int,image:String,love:Int) {
        postListener?.onStarted()
        Coroutines.main {
            try {
                newsfeedPost= NewsfeedPost(Name!!,content!!,picture!!,created!!,status!!,type!!,image!!,love!!)
                Log.e("Search", "Search" + Gson().toJson(newsfeedPost))
                val response = repository.createdNewsFeedPost(header,newsfeedPost!!)
                Log.e("response", "response" + Gson().toJson(response))
                postListener?.onSuccess(response?.message!!)
                postListener?.onEnd()

            } catch (e: ApiException) {
                postListener?.onEnd()
                postListener?.onFailure(e?.message!!)
            } catch (e: NoInternetException) {
                postListener?.onEnd()
                postListener?.onFailure(e?.message!!)
            }
        }

    }
    fun getComments(header:String,postId:Int,type:Int) {
        commentsListener?.onStarted()
        Coroutines.main {
            try {
                commentsPost= CommentsPost(postId,type)
                Log.e("Search", "Search" + Gson().toJson(commentsPost))
                val response = repository.getComments(header,commentsPost!!)
                Log.e("Search", "Search" + Gson().toJson(response))
                commentsListener?.load(response?.data!!)
                commentsListener?.onEnd()
                Log.e("Search", "Search" + Gson().toJson(response))

            } catch (e: ApiException) {
                commentsListener?.onEnd()
            } catch (e: NoInternetException) {
                commentsListener?.onEnd()
            }
        }

    }
    fun getCommentsAgain(header:String,postId:Int,type:Int) {

        Coroutines.main {
            try {
                commentsPost= CommentsPost(postId,type)
                Log.e("Search", "Search" + Gson().toJson(commentsPost))
                val response = repository.getComments(header,commentsPost!!)
                Log.e("Search", "Search" + Gson().toJson(response))

                succeslistener?.onShow()
                Log.e("Search", "Search" + Gson().toJson(response))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }

    fun updateNewsFeedPost(header:String,id:Int,Name:String,content:String,picture:String,type:Int,image:String) {
        postListener?.onStarted()
        Coroutines.main {
            try {
                ownUpdatedPost= OwnUpdatedPost(id,Name!!,content!!,picture!!,type!!,image!!)
                Log.e("Search", "Search" + Gson().toJson(ownUpdatedPost))
                val response = repository.updateOwnPost(header,ownUpdatedPost!!)
                Log.e("response", "response" + Gson().toJson(response))
                postListener?.onSuccess(response?.message!!)
                postListener?.onEnd()

            } catch (e: ApiException) {
                postListener?.onEnd()
                postListener?.onFailure(e?.message!!)
            } catch (e: NoInternetException) {
                postListener?.onEnd()
                postListener?.onFailure(e?.message!!)
            }
        }

    }
    fun updatedCommentsLikeCount(header:String,id: Int,love:Int) {
        Coroutines.main {
            try {
                likeCountPost= LikeCountPost(id,love)
                val response = repository.updatedCommentsLikeCount(header,likeCountPost!!)
                Log.e("response", "response" + Gson().toJson(response))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun createdLike(header:String,postId: Int,type:Int) {
        Coroutines.main {
            try {
                commentsPost= CommentsPost(postId,type)
                val response = repository.createdLike(header,commentsPost!!)
                Log.e("response", "response" + Gson().toJson(response))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun deletedLike(header:String,postId: Int,type:Int) {
        Coroutines.main {
            try {
                commentsPost= CommentsPost(postId,type)
                val response = repository.deletedLike(header,commentsPost!!)
                Log.e("response", "response" + Gson().toJson(response))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun createComments(header:String,Name:String,content:String,created:String,status:Int,type:Int,image:String,love:Int,postId:Int) {
        commentsPostListener?.onStarted()
        Coroutines.main {
            try {
                commentsForPost= CommentsForPost(Name!!,content!!,created!!,status!!,type!!,image!!,love!!,postId!!)
                Log.e("Search", "Search" + Gson().toJson(newsfeedPost))
                val response = repository.createComments(header,commentsForPost!!)
                Log.e("response", "response" + Gson().toJson(response))
                commentsPostListener?.onSuccess(response?.message!!)
                commentsPostListener?.onEnd()

            } catch (e: ApiException) {
                commentsPostListener?.onEnd()
                commentsPostListener?.onFailure(e?.message!!)
            } catch (e: NoInternetException) {
                commentsPostListener?.onEnd()
                commentsPostListener?.onFailure(e?.message!!)
            }
        }

    }
    fun getReply(header:String,commentId:Int) {
        replyListener?.onStarted()
        Coroutines.main {
            try {
                replyPost= ReplyPost(commentId)
                Log.e("Search", "Search" + Gson().toJson(replyPost))
                val response = repository.getReply(header,replyPost!!)
                Log.e("Search", "Search" + Gson().toJson(response))
                replyListener?.load(response?.data!!)
                replyListener?.onEnd()
                Log.e("Search", "Search" + Gson().toJson(response))

            } catch (e: ApiException) {
                replyListener?.onEnd()
            } catch (e: NoInternetException) {
                replyListener?.onEnd()
            }
        }

    }
    fun createReply(header:String,Name:String,content:String,created:String,status:Int,type:Int,image:String,commentsId:Int) {
        replyPostListener?.onStarted()
        Coroutines.main {
            try {
                replyForPost= ReplyForPost(Name!!,content!!,created!!,status!!,type!!,image!!,commentsId!!)
                Log.e("Search", "Search" + Gson().toJson(replyForPost))
                val response = repository.createReply(header,replyForPost!!)
                Log.e("response", "response" + Gson().toJson(response))
                replyPostListener?.onSuccess(response?.message!!)
                replyPostListener?.onEnd()

            } catch (e: ApiException) {
                replyPostListener?.onEnd()
                replyPostListener?.onFailure(e?.message!!)
            } catch (e: NoInternetException) {
                replyPostListener?.onEnd()
                replyPostListener?.onFailure(e?.message!!)
            }
        }

    }
    fun geReplyAgain(header:String,commentId:Int) {

        Coroutines.main {
            try {
                replyPost= ReplyPost(commentId)
                Log.e("Search", "Search" + Gson().toJson(replyPost))
                val response = repository.getReply(header,replyPost!!)
                Log.e("Search", "Search" + Gson().toJson(response))

                succeslistener?.onShow()
                Log.e("Search", "Search" + Gson().toJson(response))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun getCustomerOrderInformation(header:String,orderId:Int,shopId:Int) {

        Coroutines.main {
            try {
                cutomerOrderPost= CutomerOrderPost(orderId,shopId)
                Log.e("Search", "Search" + Gson().toJson(cutomerOrderPost))
                val response = repository.getCustomerOrderInformation(header,cutomerOrderPost!!)
                Log.e("OrderInformation", "OrderInformation" + Gson().toJson(response))
                if(response.data!=null){
                    customerOrderListener?.onShow(response?.data!!)
                }
                else{
                    customerOrderListener?.onEmpty()
                }
             //   customerOrderListener?.onShow(response?.data!!)
                Log.e("Search", "Search" + Gson().toJson(response))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun createToken(header:String,type:Int,data:String) {

        Coroutines.main {
            try {
                tokenPost= TokenPost(type,data)
                Log.e("createToken", "createToken" + Gson().toJson(tokenPost))
                val response = repository.createToken(header,tokenPost!!)
                Log.e("createToken", "createToken" + Gson().toJson(response))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun getToken(header:String,type:Int,data:String) {

        Coroutines.main {
            try {
                tokenPost= TokenPost(type,data)
                Log.e("createToken", "createToken" + Gson().toJson(tokenPost))
                val response = repository.getToken(header,tokenPost!!)
                Log.e("createToken", "createToken" + Gson().toJson(response))
                pushListener?.onLoad(response.data!!)
            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun createFirebaseId(header:String,type:Int,data:String) {

        Coroutines.main {
            try {
                tokenPost= TokenPost(type,data)
                Log.e("createToken", "createToken" + Gson().toJson(tokenPost))
                val response = repository.createFirebaseId(header,tokenPost!!)
                Log.e("createToken", "createToken" + Gson().toJson(response))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun getFirebaseId(header:String,type:Int,data:String) {

        Coroutines.main {
            try {
                tokenPost= TokenPost(type,data)
                Log.e("createToken", "createToken" + Gson().toJson(tokenPost))
                val response = repository.getFirebaseId(header,tokenPost!!)
                Log.e("createToken", "createToken" + Gson().toJson(response))
                pushListener?.onLoad(response.data!!)
            } catch (e: ApiException) {
                Log.e("createToken", "createToken" +e?.message)
            } catch (e: NoInternetException) {

            }
        }

    }

}