package com.evan.dokan.data.network.post

import com.google.gson.annotations.SerializedName

class CartOrderDetailsPost (
    @SerializedName("data")
    val data: MutableList<CartOrderPost>?
)