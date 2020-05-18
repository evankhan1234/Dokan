package com.evan.dokan.ui.spalash

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.evan.dokan.R
import com.evan.dokan.ui.auth.LoginActivity
import com.evan.dokan.ui.home.HomeActivity
import com.evan.dokan.util.SharedPreferenceUtil
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_spalash.*

class SpalashActivity : AppCompatActivity(), Animator.AnimatorListener {
    private val TAG = "SplashActivity"
    private val SPLASH_TIME_OUT: Long = 3000 // 3 sec
    private var token: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spalash)
        rorate_Clockwise(app_logo)
        FirebaseApp.initializeApp(this);
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("TAG", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
                // Get new Instance ID token
                val token = task.result?.token
                Log.e("Device_Token", token)

            })
    }
    open fun rorate_Clockwise(view: View?) {
        val rotate = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f)
        rotate.duration = SPLASH_TIME_OUT
        rotate.start()
        rotate.addListener(this)
    }

    override fun onAnimationRepeat(p0: Animator?) {

    }

    override fun onAnimationEnd(p0: Animator?) {
        //Check user already logged in or not
        token = SharedPreferenceUtil.getShared(this, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
        Log.e(TAG, token!! + "")
        if (token != null && !token?.trim().equals("") && !token.isNullOrEmpty()) {
            Intent(this, LoginActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        } else {
            Intent(this, LoginActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
        finish()
    }

    override fun onAnimationCancel(p0: Animator?) {
    }

    override fun onAnimationStart(p0: Animator?) {
    }
}