package com.evan.dokan.data.network


import com.evan.dokan.data.network.post.*
import com.evan.dokan.data.network.responses.*
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyApi {

    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Response<AuthResponse>
    @GET("customer-user-details.php")
    suspend fun getCustomerUser(
        @Header("Authorization") Authorization:String
    ): Response<CustomerResponses>

    @POST("update-like-count.php")
    suspend fun updatedLikeCount(
        @Header("Authorization") Authorization:String,
        @Body post: LikeCountPost
    ): Response<BasicResponses>
    @POST("create-love.php")
    suspend fun createdLove(
        @Header("Authorization") Authorization:String,
        @Body post: LovePost
    ): Response<BasicResponses>
    @POST("deleted-love.php")
    suspend fun deletedLove(
        @Header("Authorization") Authorization:String,
        @Body post: LovePost
    ): Response<BasicResponses>
    @POST("create-post.php")
    suspend fun createdNewsFeedPost(
        @Header("Authorization") Authorization:String,
        @Body post: NewsfeedPost
    ): Response<BasicResponses>

    @POST("update-customer-user-details.php")
    suspend fun  updateUserDetails(
        @Header("Authorization") Authorization:String,
        @Body userUpdatePost: UserUpdatePost
    ): Response<BasicResponses>
    @POST("post-pagination.php")
    suspend fun  getPublicPostPagination(
        @Header("Authorization") Authorization:String,
        @Body publicForPost: PublicForPost
    ): Response<PostResponses>
    @POST("own-post-pagination.php")
    suspend fun  getOwnPostPagination(
        @Header("Authorization") Authorization:String,
        @Body ownForPost: OwnForPost
    ): Response<PostResponses>
    @POST("update-customer-password.php")
    suspend fun  updatePassword(
        @Header("Authorization") Authorization:String,
        @Body passwordPost: PasswordPost
    ): Response<BasicResponses>
    @POST("login-customer-api.php")
    suspend fun userLoginFor(
        @Body authPost: AuthPost
    ) : Response<LoginResponse>
    @POST("searching-product-category.php")
    suspend fun getProductSearchCategory(
        @Header("Authorization") Authorization:String,
        @Body productSearchPost: ProductSearchPost
    ): Response<ProductResponses>
    @POST("customer-product-search.php")
    suspend fun getProductSearchByCustomer(
        @Header("Authorization") Authorization:String,
        @Body productSearchPost: CustomerProductSearchPost
    ): Response<ProductResponses>
    @POST("notice-get.php")
    suspend fun getNotice(
        @Header("Authorization") Authorization:String,
        @Body noticePost: NoticePost
    ): Response<NoticeResponses>
    @POST("recent-customer-product.php")
    suspend fun getRecentProduct(
        @Header("Authorization") Authorization:String,
        @Body shopUserIdPost: ShopUserIdPost
    ): Response<ProductResponses>
    @POST("product-category-product-list.php")
    suspend fun getProductCategory(
        @Header("Authorization") Authorization:String,
        @Body productPost: ProductPost
    ): Response<ProductResponses>
    @POST("customer-search-product-list-pagination.php")
    suspend fun getProductBySearchPagination(
        @Header("Authorization") Authorization:String,
        @Body post: PaginationLimit
    ): Response<ProductResponses>
    @POST("delivered-pagination.php")
    suspend fun getDeliveredPagination(
        @Header("Authorization") Authorization:String,
        @Body post: PaginationLimit
    ): Response<OrderResponses>
    @POST("customer-orders-details.php")
    suspend fun getCustomerDetailsList(
        @Header("Authorization") Authorization:String,
        @Body post: OrderIdPost
    ): Response<OrderDetailsResponse>
    @POST("processing-pagination.php")
    suspend fun getProcessingPagination(
        @Header("Authorization") Authorization:String,
        @Body post: PaginationLimit
    ): Response<OrderResponses>
    @POST("pending-pagination.php")
    suspend fun getPendingPagination(
        @Header("Authorization") Authorization:String,
        @Body post: PaginationLimit
    ): Response<OrderResponses>
    @POST("product-like.php")
    suspend fun getProductLike(
        @Header("Authorization") Authorization:String,
        @Body productLikePost: ProductLikePost
    ): Response<BasicResponses>
    @POST("create-wish-list.php")
    suspend fun  createWishList(
        @Header("Authorization") Authorization:String,
        @Body wishListCreatePost: WishListCreatePost
    ): Response<BasicResponses>
    @POST("wish-list-count.php")
    suspend fun  countWishList(
        @Header("Authorization") Authorization:String,
        @Body shopUserIdPost: ShopUserIdPost
    ): Response<CountResponses>
    @POST("cart-list-count.php")
    suspend fun  countCartList(
        @Header("Authorization") Authorization:String,
        @Body shopUserIdPost: ShopUserIdPost
    ): Response<CountResponses>
    @POST("delete-cart-items.php")
    suspend fun  deleteCartList(
        @Header("Authorization") Authorization:String,
        @Body deleteCartPost: DeleteCartPost
    ): Response<BasicResponses>
    @POST("update-cart-order-details.php")
    suspend fun  updateCartOrderDetails(
        @Header("Authorization") Authorization:String,
        @Body cartOrderDetailsPost: CartOrderDetailsPost
    ): Response<BasicResponses>

    @POST("delete-wish-list-product.php")
    suspend fun  deleteWishList(
        @Header("Authorization") Authorization:String,
        @Body wshListDeletedPost: WishListDeletedPost
    ): Response<BasicResponses>
    @POST("wish-list-get.php")
    suspend fun  getWishList(
        @Header("Authorization") Authorization:String,
        @Body shopUserIdPost: ShopUserIdPost
    ): Response<ProductResponses>
    @POST("create-customer-order.php")
    suspend fun  postOrderList(
        @Header("Authorization") Authorization:String,
        @Body customerOrderPost: CustomerOrderPost
    ): Response<BasicResponses>
    @POST("update-cart-quantity.php")
    suspend fun  updateCartQuantity(
        @Header("Authorization") Authorization:String,
        @Body cartListQuantityPost: CartListQuantityPost
    ): Response<BasicResponses>
    @POST("cart-list-get.php")
    suspend fun  getCartList(
        @Header("Authorization") Authorization:String,
        @Body shopUserIdPost: ShopUserIdPost
    ): Response<CartListResponses>
    @POST("create-cart-list.php")
    suspend fun  createCart(
        @Header("Authorization") Authorization:String,
        @Body cartCreatePost: CartCreatePost
    ): Response<BasicResponses>
    @POST("customer-product-category.php")
    suspend fun getCategory(
        @Header("Authorization") Authorization:String,
        @Body shopUserIdPost: ShopUserIdPost
    ): Response<CategoryResponses>
    @POST("nearby-shop.php")
    suspend fun getShopPagination(
        @Header("Authorization") Authorization:String,
        @Body limit: ShopPost
    ) : Response<ShopResponse>
    @POST("searching-shop.php")
    suspend fun getShopSearch(
        @Header("Authorization") Authorization:String,
        @Body searchPost: SearchPost
    ): Response<ShopResponse>
    @POST("create-customer-registration.php")
    suspend fun userSignUp(
        @Body post: SignUpPost
    ) : Response<BasicResponses>
    @FormUrlEncoded
    @POST("signup")
    suspend fun userSignup(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : Response<AuthResponse>
    @Multipart
    @POST("create-sign-up-image.php")
    suspend fun createProfileImage(
        @Part file: MultipartBody.Part?, @Part("uploaded_file") requestBody: RequestBody?
    ): Response<ImageResponse>
    @GET("quotes")
    suspend fun getQuotes() : Response<QuotesResponse>

    companion object{
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : MyApi{

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("http://192.168.0.106/stationary/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }

}

