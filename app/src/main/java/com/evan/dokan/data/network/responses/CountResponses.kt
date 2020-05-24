package com.evan.dokan.data.network.responses

import com.google.gson.annotations.SerializedName

class CountResponses (
    @SerializedName("success")
    val success : Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("count")
    val count: Int?
)