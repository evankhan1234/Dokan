package com.evan.dokan.data.db.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class OrderDetails (
    @SerializedName("Name")
    var Name: String?,
    @SerializedName("Quantity")
    var Quantity: Int?,
    @SerializedName("Price")
    var Price: Double?,
    @SerializedName("Picture")
    var Picture: String?,
    @SerializedName("Created")
    var Created: String?
): Parcelable