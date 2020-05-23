package com.evan.dokan.data.network.post

import com.google.gson.annotations.SerializedName

class CartOrderPost (
    @SerializedName("ShopUserId")
    val ShopUserId: Int?,
    @SerializedName("ProductId")
    val ProductId: Int?,
    @SerializedName("Status")
    val Status: Int?
)