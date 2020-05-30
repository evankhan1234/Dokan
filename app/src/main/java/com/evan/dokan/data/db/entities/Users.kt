package com.evan.dokan.data.db.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
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
    @SerializedName("Gender")
    var Gender: Int?,
    @SerializedName("Created")
    var Created: String?
):Parcelable