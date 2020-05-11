package com.evan.dokan.data.network.responses

import com.evan.dokan.data.db.entities.Quote


data class QuotesResponse (
    val isSuccessful: Boolean,
    val quotes: List<Quote>
)