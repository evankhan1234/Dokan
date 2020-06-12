package com.evan.dokan.data.network.responses

import com.evan.dokan.data.db.entities.CustomerOrder
import com.google.gson.annotations.SerializedName

class CustomerOrderResponses (
    @SerializedName("success")
    var success : Boolean?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: Int?,
    @SerializedName("data")
    var data: CustomerOrder?
)