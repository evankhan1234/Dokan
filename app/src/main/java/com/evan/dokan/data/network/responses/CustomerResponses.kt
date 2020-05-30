package com.evan.dokan.data.network.responses

import com.evan.dokan.data.db.entities.Users
import com.google.gson.annotations.SerializedName

class CustomerResponses (
    @SerializedName("status")
    val status: Int?,
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: Users?
)