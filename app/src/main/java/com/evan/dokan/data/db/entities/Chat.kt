package com.evan.dokan.data.db.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Chat(
    @SerializedName("Name")
    var sender: String?="",
    @SerializedName("receiver")
    var receiver: String?="",
    @SerializedName("message")
    var message: String?="",
    @SerializedName("isseen")
    var isseen: Boolean?=false
): Parcelable