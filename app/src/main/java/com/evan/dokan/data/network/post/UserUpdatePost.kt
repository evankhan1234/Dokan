package com.evan.dokan.data.network.post

import com.google.gson.annotations.SerializedName

class UserUpdatePost  (
    @SerializedName("Id")
    val Id: Int?,
    @SerializedName("Name")
    val Name: String?,
    @SerializedName("Address")
    val Address: String?,
    @SerializedName("Picture")
    val Picture: String?,
    @SerializedName("Gender")
    val Gender: Int?
)