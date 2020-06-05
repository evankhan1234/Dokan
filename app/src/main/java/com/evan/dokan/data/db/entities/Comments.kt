package com.evan.dokan.data.db.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Comments (
    @SerializedName("Id")
    var Id: Int?,
    @SerializedName("Content")
    var Content: String?,
    @SerializedName("UserImage")
    var UserImage: String?,
    @SerializedName("UserName")
    var UserName: String?,
    @SerializedName("Created")
    var Created: String?,
    @SerializedName("Love")
    var Love: Int?,
    @SerializedName("Type")
    var Type: Int?,
    @SerializedName("IsValue")
    var IsValue: Boolean?
): Parcelable