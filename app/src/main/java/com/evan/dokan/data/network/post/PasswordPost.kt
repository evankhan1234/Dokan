package com.evan.dokan.data.network.post

import com.google.gson.annotations.SerializedName

class PasswordPost (
    @SerializedName("Id")
    val Id: Int?,
    @SerializedName("Password")
    val Password: String?

)