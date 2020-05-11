package com.evan.dokan.data.network.responses

import com.evan.dokan.data.db.entities.User


data class AuthResponse(
    val isSuccessful : Boolean?,
    val message: String?,
    val user: User?
)