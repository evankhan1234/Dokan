package com.evan.dokan.data.db.entities

import com.google.gson.annotations.SerializedName

data class Shop (
    @SerializedName("Id")
    var Id: Int?,
    @SerializedName("Name")
    var Name: String?,
    @SerializedName("LicenseNumber")
    var LicenseNumber: String?,
    @SerializedName("Address")
    var Address: String?,
    @SerializedName("ShopUserId")
    var ShopUserId: Int?,
    @SerializedName("Status")
    var Status: Int?,
    @SerializedName("Created")
    var Created: String?
)