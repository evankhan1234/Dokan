package com.evan.dokan.data.network.post

import com.google.gson.annotations.SerializedName

class OrderPost (
    @SerializedName("Name")
    val Name: String?,
    @SerializedName("Quantity")
    val Quantity: Int?,
    @SerializedName("Price")
    val Price: Double?,
    @SerializedName("ProductId")
    val ProductId: Int?,
    @SerializedName("OrderStatus")
    val OrderStatus: Int?,
    @SerializedName("ShopId")
    val ShopId: Int?,
    @SerializedName("Picture")
    val Picture: String?,
    @SerializedName("Created")
    val Created: String?
)