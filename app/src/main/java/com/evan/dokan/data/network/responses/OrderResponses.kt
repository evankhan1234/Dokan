package com.evan.dokan.data.network.responses

import com.evan.dokan.data.db.entities.Category
import com.evan.dokan.data.db.entities.Order
import com.google.gson.annotations.SerializedName

class OrderResponses (
    @SerializedName("success")
    val success : Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("data")
    val data: MutableList<Order>?
)