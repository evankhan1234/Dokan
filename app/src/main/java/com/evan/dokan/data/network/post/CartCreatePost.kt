package com.evan.dokan.data.network.post

import com.google.gson.annotations.SerializedName

class CartCreatePost (
    @SerializedName("ProductName")
    val ProductName: String?,
    @SerializedName("Price")
    val Price: Double?,
    @SerializedName("Quantity")
    val Quantity: Int?,
    @SerializedName("ProductId")
    val ProductId: Int?,
    @SerializedName("Status")
    val Status: Int?,
    @SerializedName("ShopUserId")
    val ShopUserId: Int?,
    @SerializedName("Picture")
    val Picture: String?,
    @SerializedName("Created")
    val Created: String?
)