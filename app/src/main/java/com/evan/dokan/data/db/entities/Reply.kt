package com.evan.dokan.data.db.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Reply (
    @SerializedName("Id")
    var Id: Int?,
    @SerializedName("Content")
    var Content: String?,
    @SerializedName("UserImage")
    var UserImage: String?,
    @SerializedName("Username")
    var Username: String?,
    @SerializedName("Created")
    var Created: String?,
    @SerializedName("CommentsId")
    var CommentsId: Int?,
    @SerializedName("Type")
    var Type: Int?,
    @SerializedName("Status")
    var Status: Int?
): Parcelable