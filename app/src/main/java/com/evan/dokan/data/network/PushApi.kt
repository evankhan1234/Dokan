package com.evan.dokan.data.network

import com.evan.dokan.data.network.post.*
import com.evan.dokan.data.network.responses.*
import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface PushApi {


    @POST("fcm/send")
    suspend fun  sendPush(
        @Header("Authorization") Authorization:String,
        @Body pushPost: PushPost
    ): Response<PushResponses>


    companion object{
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : PushApi{

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()
            val gson = GsonBuilder()
                .setLenient()
                .create()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("https://fcm.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(PushApi::class.java)
        }
    }

}