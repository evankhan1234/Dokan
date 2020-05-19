package com.evan.dokan.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.evan.dokan.ui.home.HomeActivity
import kotlinx.android.synthetic.main.activity_login.*
import com.evan.dokan.R
import com.evan.dokan.data.db.entities.Users
import com.evan.dokan.databinding.ActivityLoginBinding
import com.evan.dokan.ui.auth.interfaces.AuthListener
import com.evan.dokan.util.MyPasswordTransformationMethod
import com.evan.dokan.util.hide
import com.evan.dokan.util.show
import com.evan.dokan.util.snackbar
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class LoginActivity : AppCompatActivity(),
    AuthListener, KodeinAware {

    override val kodein by kodein()
    private val factory : AuthViewModelFactory by instance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.authListener = this
        et_password?.transformationMethod = MyPasswordTransformationMethod()

        show_pass?.setOnClickListener {
            onPasswordVisibleOrInvisible()
        }
        btn_create_account?.setOnClickListener {
            Intent(this, CreateAccountActivity::class.java).also {

                startActivity(it)
            }
        }
    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess(user: Users) {
        progress_bar.hide()
        Intent(this, HomeActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }

    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }
    fun onPasswordVisibleOrInvisible() {
        val cursorPosition = et_password?.selectionStart

        if (et_password?.transformationMethod == null) {
            et_password?.transformationMethod = MyPasswordTransformationMethod()
            show_pass?.isSelected = false
        } else {

            et_password?.transformationMethod = null
            show_pass?.isSelected = true
        }
        et_password?.setSelection(cursorPosition!!)
    }
}
