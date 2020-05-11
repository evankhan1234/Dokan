package com.evan.dokan.data.network.post

data class LoginResponse(
    val status: Int?,
    val success: Boolean?,
    val jwt: String?,
    val message: String?,
    val data: Data?
)