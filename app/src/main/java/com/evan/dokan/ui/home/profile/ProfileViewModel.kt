package com.evan.dokan.ui.home.profile

import android.util.Log
import androidx.lifecycle.ViewModel;
import com.evan.dokan.data.db.entities.User
import com.evan.dokan.data.repositories.UserRepository
import com.evan.dokan.util.Coroutines
import com.evan.dokan.util.lazyDeferred


class ProfileViewModel(
    private val  repository: UserRepository
) : ViewModel() {

    fun user() = repository.getUser()
   // fun userList() = repository.getUserList()
    val quotes by lazyDeferred {
        repository.getUserList()
    }

    fun saveUser(user: User){
        Coroutines.main {
            Log.e("sdfds","Sds"+user.created_at)
            repository.saveUser(user)
        }

    }


}
