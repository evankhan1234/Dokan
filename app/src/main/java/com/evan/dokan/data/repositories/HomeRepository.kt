package com.evan.dokan.data.repositories

import com.evan.dokan.data.db.AppDatabase
import com.evan.dokan.data.db.entities.User
import com.evan.dokan.data.network.MyApi
import com.evan.dokan.data.network.SafeApiRequest
import com.evan.dokan.data.network.post.*
import com.evan.dokan.data.network.responses.*
import okhttp3.MultipartBody
import okhttp3.RequestBody

class HomeRepository (
    private val api: MyApi

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

}