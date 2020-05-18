package com.evan.dokan.data.db.entities

import com.google.gson.annotations.SerializedName

data class Users (
    @SerializedName("Id")
    var Id: Int?,
    @SerializedName("Name")
    var Name: String?,
    @SerializedName("MobileNumber")
    var MobileNumber: String?,
    @SerializedName("Address")
    var Address: String?,
    @SerializedName("Email")
    var Email: String?,
    @SerializedName("Password")
    var Password: String?,
    @SerializedName("Picture")
    var Picture: String?,
    @SerializedName("Status")
    var Status: Int?,
    @SerializedName("Created")
    var Created: String?
)