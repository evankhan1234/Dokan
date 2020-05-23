package com.evan.dokan.data.network.post

import com.google.gson.annotations.SerializedName

class CustomerOrderPost (
    @SerializedName("ShopId")
    val ShopId: Int?,
    @SerializedName("Status")
    val Status: Int?,
    @SerializedName("Created")
    val Created: String?,
    @SerializedName("OrderAddress")
    val OrderAddress: String?,
    @SerializedName("OrderArea")
    val OrderArea: String?,
    @SerializedName("OrderLatitude")
    val OrderLatitude: Double?,
    @SerializedName("OrderLongitude")
    val OrderLongitude: Double?,
    @SerializedName("data")
    val data: MutableList<OrderPost>?
)