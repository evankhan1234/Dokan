package com.evan.dokan.data.db.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class FIrebaseUser (
    @SerializedName("id")
    var id: String?="",
    @SerializedName("username")
    var username: String?="",
    @SerializedName("imageURL")
    var imageURL: String?="",
    @SerializedName("status")
    var status: String?="",
    @SerializedName("search")
    var search: String?=""
): Parcelable