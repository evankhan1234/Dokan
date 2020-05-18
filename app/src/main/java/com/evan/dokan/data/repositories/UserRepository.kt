package com.evan.dokan.data.repositories

import com.evan.dokan.data.db.AppDatabase
import com.evan.dokan.data.db.entities.User
import com.evan.dokan.data.network.MyApi
import com.evan.dokan.data.network.SafeApiRequest
import com.evan.dokan.data.network.post.AuthPost
import com.evan.dokan.data.network.post.SignUpPost
import com.evan.dokan.data.network.responses.LoginResponse
import com.evan.dokan.data.network.responses.AuthResponse
import com.evan.dokan.data.network.responses.BasicResponses
import com.evan.dokan.data.network.responses.ImageResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody


class UserRepository(
    private val api: MyApi,
    private val db: AppDatabase
) : SafeApiRequest() {

    suspend fun userLogin(email: String, password: String): AuthResponse {
        return apiRequest { api.userLogin(email, password) }
    }
    suspend fun userLoginFor(auth: AuthPost): LoginResponse {
        return apiRequest { api.userLoginFor(auth) }
    }
    suspend fun userSignUp(auth: SignUpPost): BasicResponses {
        return apiRequest { api.userSignUp(auth) }
    }


    suspend fun saveUser(user: User) =
        db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getuser()
    fun getUserList() = db.getUserDao().getuserList()
    suspend fun createImage(part: MultipartBody.Part, body: RequestBody): ImageResponse {
        return apiRequest { api.createProfileImage(part,body) }
    }
}