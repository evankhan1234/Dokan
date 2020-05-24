package com.evan.dokan.data.network.post

import com.google.gson.annotations.SerializedName

class CartListQuantityPost (
    @SerializedName("ShopUserId")
    val ShopUserId: Int?,
    @SerializedName("ProductId")
    val ProductId: Int?,
    @SerializedName("Quantity")
    val Quantity: Int?
)