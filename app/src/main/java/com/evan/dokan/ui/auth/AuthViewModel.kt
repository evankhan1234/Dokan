package com.evan.dokan.ui.auth

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.evan.dokan.data.network.post.AuthPost
import com.evan.dokan.data.repositories.UserRepository
import com.evan.dokan.util.ApiException
import com.evan.dokan.util.Coroutines
import com.evan.dokan.util.NoInternetException
import com.evan.dokan.util.SharedPreferenceUtil


class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    var authPost: AuthPost? = null
    var name: String? = null
    var email: String? = null
    var password: String? = null
    var passwordconfirm: String? = null

    var authListener: AuthListener? = null

    fun getLoggedInUser() = repository.getUser()


    fun onLoginButtonClick(view: View) {
        authListener?.onStarted()
        if ( email.isNullOrEmpty()) {
            authListener?.onFailure("Email is Empty")
            return
        }
        else if ( password.isNullOrEmpty()) {
            authListener?.onFailure("Password is Empty")
            return
        }

        Coroutines.main {
            try {
                authPost = AuthPost(email!!, password!!)
                val authResponse = repository.userLoginFor(authPost!!)
                Log.e("response", "response" + authResponse.message)
                if(authResponse.success!!)
                {

                    SharedPreferenceUtil.saveShared(
                        view.context,
                        SharedPreferenceUtil.TYPE_AUTH_TOKEN,
                        authResponse.jwt!!
                    )
                    authListener?.onSuccess(authResponse.data!!)

                }
                else{
                    authListener?.onFailure(authResponse.message!!)
                }

            } catch (e: ApiException) {
                authListener?.onFailure(e?.message!!)
                authListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                authListener?.onFailure(e?.message!!)
                authListener?.onFailure(e.message!!)
            }
        }

    }







}