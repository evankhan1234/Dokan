package com.evan.dokan.data.db.entities

import com.google.gson.annotations.SerializedName

class CustomerOrder (
    @SerializedName("Discount")
    var Discount: String?,
    @SerializedName("Total")
    var Total: String?,
    @SerializedName("PaidAmount")
    var PaidAmount: String?,
    @SerializedName("DueAmount")
    var DueAmount: String?,
    @SerializedName("InvoiceNumber")
    var InvoiceNumber: String?,
    @SerializedName("OrderDetails")
    var OrderDetails: String?,
    @SerializedName("DeliveryCharge")
    var DeliveryCharge: String?
)