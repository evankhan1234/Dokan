package com.evan.dokan.data.db.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product (
    @SerializedName("Id")
    var Id: Int?,
    @SerializedName("Name")
    var Name: String?,
    @SerializedName("Details")
    var Details: String?,
    @SerializedName("ProductCode")
    var ProductCode: String?,
    @SerializedName("ProductImage")
    var ProductImage: String?,
    @SerializedName("UnitId")
    var UnitId: Int?,
    @SerializedName("SellPrice")
    var SellPrice: Double?,
    @SerializedName("SupplierPrice")
    var SupplierPrice: Double?,
    @SerializedName("SupplierId")
    var SupplierId: Int?,
    @SerializedName("ShopId")
    var ShopId: Int?,
    @SerializedName("Stock")
    var Stock: Int?,
    @SerializedName("Discount")
    var Discount: Double?,
    @SerializedName("ShopUserId")
    var ShopUserId: Int?,
    @SerializedName("Created")
    var Created: String?,
    @SerializedName("ProductCategoryId")
    var ProductCategoryId: Int?,
    @SerializedName("Status")
    var Status: Int?,
    @SerializedName("UnitName")
    var UnitName: String?,
    @SerializedName("WishId")
    var WishId: Int?,
    @SerializedName("Date")
    var Date: String?
): Parcelable