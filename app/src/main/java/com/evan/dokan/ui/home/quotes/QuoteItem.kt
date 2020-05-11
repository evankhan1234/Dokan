package com.evan.dokan.ui.home.quotes

import com.evan.dokan.R
import com.evan.dokan.data.db.entities.Quote
import com.evan.dokan.databinding.ItemQuoteBinding
import com.xwray.groupie.databinding.BindableItem


class QuoteItem(
    private val quote: Quote
) : BindableItem<ItemQuoteBinding>(){

    override fun getLayout() = R.layout.item_quote

    override fun bind(viewBinding: ItemQuoteBinding, position: Int) {
        viewBinding.setQuote(quote)
    }
}