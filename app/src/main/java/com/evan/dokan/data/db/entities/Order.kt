package com.evan.dokan.data.db.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Order (
    @SerializedName("Id")
    var Id: String?,
    @SerializedName("Name")
    var Name: String?,
    @SerializedName("MobileNumber")
    var MobileNumber: String?,
    @SerializedName("Email")
    var Email: String?,
    @SerializedName("Picture")
    var Picture: String?,
    @SerializedName("OrderAddress")
    var OrderAddress: String?,
    @SerializedName("OrderArea")
    var OrderArea: String?,
    @SerializedName("Created")
    var Created: String?
): Parcelable