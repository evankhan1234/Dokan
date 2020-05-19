package com.evan.dokan.data.repositories

import com.evan.dokan.data.db.AppDatabase
import com.evan.dokan.data.db.entities.User
import com.evan.dokan.data.network.MyApi
import com.evan.dokan.data.network.SafeApiRequest
import com.evan.dokan.data.network.post.AuthPost
import com.evan.dokan.data.network.post.LimitPost
import com.evan.dokan.data.network.post.SearchPost
import com.evan.dokan.data.network.post.SignUpPost
import com.evan.dokan.data.network.responses.*
import okhttp3.MultipartBody
import okhttp3.RequestBody

class HomeRepository (
    private val api: MyApi

) : SafeApiRequest() {



    suspend fun getShopPagination(header:String,post: LimitPost): ShopResponse {
        return apiRequest { api.getShopPagination(header,post) }
    }
    suspend fun getShopSearch(header:String,post: SearchPost): ShopResponse {
        return apiRequest { api.getShopSearch(header,post) }
    }

}