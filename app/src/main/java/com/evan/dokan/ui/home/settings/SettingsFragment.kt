package com.evan.dokan.ui.home.settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.evan.dokan.R
import com.evan.dokan.data.db.entities.Users
import com.evan.dokan.ui.auth.LoginActivity
import com.evan.dokan.ui.home.HomeActivity
import com.evan.dokan.ui.home.HomeViewModel
import com.evan.dokan.ui.home.HomeViewModelFactory
import com.evan.dokan.util.SharedPreferenceUtil
import com.evan.dokan.util.hide
import com.evan.dokan.util.show
import de.hdodenhof.circleimageview.CircleImageView

import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class SettingsFragment : Fragment() ,KodeinAware,IUserListener{
    override val kodein by kodein()
    var progress_bar: ProgressBar?=null
    private val factory : HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    var token:String?=""
    var img_icon:CircleImageView?=null
    var text_name:TextView?=null
    var text_phone_number:TextView?=null
    var text_email:TextView?=null
    var linear_profile:LinearLayout?=null
    var linear_change_password:LinearLayout?=null
    var linear_logout:LinearLayout?=null
    var user:Users?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_settings, container, false)

        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        viewModel.userListener=this

        linear_change_password=root?.findViewById(R.id.linear_change_password)
        linear_logout=root?.findViewById(R.id.linear_logout)
        text_phone_number=root?.findViewById(R.id.text_phone_number)
        linear_profile=root?.findViewById(R.id.linear_profile)
        progress_bar=root?.findViewById(R.id.progress_bar)
        text_name=root?.findViewById(R.id.text_name)
        img_icon=root?.findViewById(R.id.img_icon)
        text_email=root?.findViewById(R.id.text_email)

        token = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
        viewModel?.getCustomerUser(token!!)
        linear_profile?.setOnClickListener {
            if (activity is HomeActivity) {
                (activity as HomeActivity).goToProfileUpdateFragment(user!!)
            }
        }
        linear_change_password?.setOnClickListener {
            if (activity is HomeActivity) {
                (activity as HomeActivity).goToChangePasswordFragment(user!!)
            }
        }
        linear_logout?.setOnClickListener {
            Toast.makeText(context!!,"Successfully Logout", Toast.LENGTH_SHORT).show()
            SharedPreferenceUtil.saveShared(context!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN, "")
            SharedPreferenceUtil.saveShared(context!!, SharedPreferenceUtil.TYPE_FRESH, "")
            val intent=Intent(context!!,LoginActivity::class.java)
            startActivity(intent)
            if (activity is HomeActivity) {
                (activity as HomeActivity).finishs()
            }
        }
        return root
    }
    override fun onStarted() {

        progress_bar?.show()
//
    }

    override fun onEnd() {
        progress_bar?.hide()
    }

    override fun onUser(users: Users) {
        user=users
        Glide.with(context!!)
            .load(users?.Picture)
            .into(img_icon!!)
        text_name?.setText(users?.Name)
        text_phone_number?.setText(users?.MobileNumber)
        text_email?.setText(users?.Email)
    }

}
