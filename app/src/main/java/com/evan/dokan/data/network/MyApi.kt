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

    @POST("login-customer-api.php")
    suspend fun userLoginFor(
        @Body authPost: AuthPost
    ) : Response<LoginResponse>
    @POST("searching-product-category.php")
    suspend fun getProductSearchCategory(
        @Header("Authorization") Authorization:String,
        @Body productSearchPost: ProductSearchPost
    ): Response<ProductResponses>
    @POST("product-category-product-list.php")
    suspend fun getProductCategory(
        @Header("Authorization") Authorization:String,
        @Body productPost: ProductPost
    ): Response<ProductResponses>
    @POST("product-like.php")
    suspend fun getProductLike(
        @Header("Authorization") Authorization:String,
        @Body productLikePost: ProductLikePost
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

