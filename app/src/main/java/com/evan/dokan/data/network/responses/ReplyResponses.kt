package com.evan.dokan.data.network.responses

import com.evan.dokan.data.db.entities.Comments
import com.evan.dokan.data.db.entities.Reply
import com.google.gson.annotations.SerializedName

class ReplyResponses (
    @SerializedName("success")
    val success : Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("data")
    val data: MutableList<Reply>?
)