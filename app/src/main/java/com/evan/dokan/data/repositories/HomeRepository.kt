package com.evan.dokan.data.repositories

import com.evan.dokan.data.db.AppDatabase
import com.evan.dokan.data.db.entities.User
import com.evan.dokan.data.network.MyApi
import com.evan.dokan.data.network.PushApi
import com.evan.dokan.data.network.SafeApiRequest
import com.evan.dokan.data.network.post.*
import com.evan.dokan.data.network.responses.*
import okhttp3.MultipartBody
import okhttp3.RequestBody

class HomeRepository (
    private val api: MyApi,
    private val push_api: PushApi

    ) : SafeApiRequest() {



    suspend fun getShopPagination(header:String,post: ShopPost): ShopResponse {
        return apiRequest { api.getShopPagination(header,post) }
    }
    suspend fun getShopSearch(header:String,post: SearchPost): ShopResponse {
        return apiRequest { api.getShopSearch(header,post) }
    }
     suspend fun getCategory(header:String, shopUserIdPost: ShopUserIdPost): CategoryResponses {
        return apiRequest { api.getCategory(header,shopUserIdPost) }
    }
    suspend fun getProductCategory(header:String, post: ProductPost): ProductResponses {
        return apiRequest { api.getProductCategory(header,post) }
    }
    suspend fun getProductSearchCategory(header:String, post: ProductSearchPost): ProductResponses {
        return apiRequest { api.getProductSearchCategory(header,post) }
    }
    suspend fun getProductLike(header:String, post: ProductLikePost): BasicResponses {
        return apiRequest { api.getProductLike(header,post) }
    }
    suspend fun createWishList(header:String, post: WishListCreatePost): BasicResponses {
        return apiRequest { api.createWishList(header,post) }
    }
    suspend fun deleteWishList(header:String, post: WishListDeletedPost): BasicResponses {
        return apiRequest { api.deleteWishList(header,post) }
    }
    suspend fun getWishList(header:String, post: ShopUserIdPost): ProductResponses {
        return apiRequest { api.getWishList(header,post) }
    }
    suspend fun createCart(header:String, post: CartCreatePost): BasicResponses {
        return apiRequest { api.createCart(header,post) }
    }
    suspend fun getCartList(header:String, post: ShopUserIdPost): CartListResponses {
        return apiRequest { api.getCartList(header,post) }
    }
    suspend fun updateCartQuantity(header:String, post: CartListQuantityPost): BasicResponses {
        return apiRequest { api.updateCartQuantity(header,post) }
    }
    suspend fun postOrderList(header:String, post: CustomerOrderPost): BasicResponses {
        return apiRequest { api.postOrderList(header,post) }
    }
    suspend fun updateCartOrderDetails(header:String, post: CartOrderDetailsPost): BasicResponses {
        return apiRequest { api.updateCartOrderDetails(header,post) }
    }
    suspend fun deleteCartList(header:String, post: DeleteCartPost): BasicResponses {
        return apiRequest { api.deleteCartList(header,post) }
    }
    suspend fun sendPush(header:String, post: PushPost): PushResponses {
        return apiRequest { push_api.sendPush(header,post) }
    }
    suspend fun countWishList(header:String, post: ShopUserIdPost): CountResponses {
        return apiRequest { api.countWishList(header,post) }
    }
    suspend fun countCartList(header:String, post: ShopUserIdPost): CountResponses {
        return apiRequest { api.countCartList(header,post) }
    }
    suspend fun getPendingPagination(header:String,post: PaginationLimit): OrderResponses {
        return apiRequest { api.getPendingPagination(header,post) }
    }

    suspend fun getProcessingPagination(header:String,post: PaginationLimit): OrderResponses {
        return apiRequest { api.getProcessingPagination(header,post) }
    }
    suspend fun getDeliveredPagination(header:String,post: PaginationLimit): OrderResponses {
        return apiRequest { api.getDeliveredPagination(header,post) }
    }
    suspend fun getCustomerDetailsList(header:String,post: OrderIdPost): OrderDetailsResponse {
        return apiRequest { api.getCustomerDetailsList(header,post) }
    }
    suspend fun getProductBySearchPagination(header:String,post: PaginationLimit): ProductResponses {
        return apiRequest { api.getProductBySearchPagination(header,post) }
    }
    suspend fun getProductSearchByCustomer(header:String,post: CustomerProductSearchPost): ProductResponses {
        return apiRequest { api.getProductSearchByCustomer(header,post) }
    }
    suspend fun getCustomerUser(header:String): CustomerResponses {
        return apiRequest { api.getCustomerUser(header) }
    }
    suspend fun updateUserDetails(header:String,post: UserUpdatePost): BasicResponses {
        return apiRequest { api.updateUserDetails(header,post) }
    }
    suspend fun updatePassword(header:String,post: PasswordPost): BasicResponses {
        return apiRequest { api.updatePassword(header,post) }
    }
    suspend fun getRecentProduct(header:String,post: ShopUserIdPost): ProductResponses {
        return apiRequest { api.getRecentProduct(header,post) }
    }
    suspend fun getNotice(header:String,post: NoticePost): NoticeResponses {
        return apiRequest { api.getNotice(header,post) }
    }
    suspend fun getPublicPostPagination(header:String,post: PublicForPost): PostResponses {
        return apiRequest { api.getPublicPostPagination(header,post) }
    }
    suspend fun getOwnPostPagination(header:String,post: OwnForPost): PostResponses {
        return apiRequest { api.getOwnPostPagination(header,post) }
    }
    suspend fun updatedLikeCount(header:String,post: LikeCountPost): BasicResponses {
        return apiRequest { api.updatedLikeCount(header,post) }
    }
    suspend fun createdLove(header:String,post: LovePost): BasicResponses {
        return apiRequest { api.createdLove(header,post) }
    }
    suspend fun deletedLove(header:String,post: LovePost): BasicResponses {
        return apiRequest { api.deletedLove(header,post) }
    }
    suspend fun createdNewsFeedPost(header:String,post: NewsfeedPost): BasicResponses {
        return apiRequest { api.createdNewsFeedPost(header,post) }
    }
    suspend fun getComments(header:String,post: CommentsPost): CommentsResponse {
        return apiRequest { api.getComments(header,post) }
    }
    suspend fun createComments(header:String,post: CommentsForPost): BasicResponses {
        return apiRequest { api.createComments(header,post) }
    }
    suspend fun updateOwnPost(header:String,post: OwnUpdatedPost): BasicResponses {
        return apiRequest { api.updateOwnPost(header,post) }
    }
    suspend fun createdLike(header:String,post: CommentsPost): BasicResponses {
        return apiRequest { api.createdLike(header,post) }
    }
    suspend fun deletedLike(header:String,post: CommentsPost): BasicResponses {
        return apiRequest { api.deletedLike(header,post) }
    }
    suspend fun updatedCommentsLikeCount(header:String,post: LikeCountPost): BasicResponses {
        return apiRequest { api.updatedCommentsLikeCount(header,post) }
    }
    suspend fun createReply(header:String,post: ReplyForPost): BasicResponses {
        return apiRequest { api.createReply(header,post) }
    }
    suspend fun getReply(header:String,post: ReplyPost): ReplyResponses {
        return apiRequest { api.getReply(header,post) }
    }
    suspend fun getCustomerOrderInformation(header:String,post: CutomerOrderPost): CustomerOrderResponses {
        return apiRequest { api.getCustomerOrderInformation(header,post) }
    }
    suspend fun createToken(header:String,tokenPost: TokenPost): BasicResponses {
        return apiRequest { api.createToken(header,tokenPost) }
    }
    suspend fun getToken(header:String,tokenPost: TokenPost): TokenResponses {
        return apiRequest { api.getToken(header,tokenPost) }
    }
    suspend fun createFirebaseId(header:String,tokenPost: TokenPost): BasicResponses {
        return apiRequest { api.createFirebaseId(header,tokenPost) }
    }
    suspend fun getFirebaseId(header:String,tokenPost: TokenPost): TokenResponses {
        return apiRequest { api.getFirebaseId(header,tokenPost) }
    }
    suspend fun createChat(header:String,post: ChatPost): BasicResponses {
        return apiRequest { api.createChat(header,post) }
    }
    suspend fun getShopBy(header:String, post: ShopIdPost): ShopResponses {
        return apiRequest { api.getShopBy(header,post) }
    }
}