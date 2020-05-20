package com.evan.dokan.data.network.responses

import com.evan.dokan.data.db.entities.Category
import com.google.gson.annotations.SerializedName

data class CategoryResponses (
    @SerializedName("success")
    val success : Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("data")
    val data: MutableList<Category>?
)