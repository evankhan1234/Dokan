package com.evan.dokan.data.db.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category (
    @SerializedName("Id")
    var Id: String?,
    @SerializedName("Name")
    var Name: String?,
    @SerializedName("Status")
    var Status: Int?,
    @SerializedName("ShopId")
    var ShopId: Int?,
    @SerializedName("created")
    var created: String?,
    @SerializedName("ShopUserId")
    var ShopUserId: Int?
): Parcelable