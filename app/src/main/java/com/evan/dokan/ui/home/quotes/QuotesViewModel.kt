package com.evan.dokan.ui.home.quotes

import android.util.Log
import androidx.lifecycle.ViewModel;
import com.evan.dokan.data.db.entities.Quote
import com.evan.dokan.data.repositories.QuotesRepository
import com.evan.dokan.util.Coroutines
import com.evan.dokan.util.lazyDeferred


class QuotesViewModel(
   private val repository: QuotesRepository
) : ViewModel() {

    val quotes by lazyDeferred {
        repository.getQuotes()
    }
    fun saveUser(quote: Quote){
        Coroutines.main {
            Log.e("sdfds","Sds"+quote.created_at)
            repository.saveUser(quote)
        }

    }
}
