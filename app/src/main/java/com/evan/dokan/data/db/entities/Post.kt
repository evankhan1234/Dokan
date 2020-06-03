package com.evan.dokan.data.db.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Post (
    @SerializedName("Id")
    var Id: Int?,
    @SerializedName("Name")
    var Name: String?,
    @SerializedName("Image")
    var Image: String?,
    @SerializedName("UserId")
    var UserId: Double?,
    @SerializedName("Content")
    var Content: String?,
    @SerializedName("Picture")
    var Picture: String?,
    @SerializedName("Created")
    var Created: String?,
    @SerializedName("Love")
    var Love: Int?,
    @SerializedName("Type")
    var Type: Int?,
    @SerializedName("value")
    var value: Boolean,
    @SerializedName("UserForId")
    var UserForId: Int?
): Parcelable