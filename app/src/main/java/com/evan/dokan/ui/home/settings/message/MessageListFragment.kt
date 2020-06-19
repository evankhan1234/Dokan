package com.evan.dokan.ui.home.settings.message

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.evan.dokan.R
import com.evan.dokan.ui.home.HomeViewModel
import com.evan.dokan.ui.home.HomeViewModelFactory
import com.evan.dokan.ui.home.cart.IPushListener
import com.evan.dokan.util.SharedPreferenceUtil
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class MessageListFragment : Fragment(),KodeinAware, IPushListener {
    override val kodein by kodein()
    var progress_bar: ProgressBar?=null
    private val factory : HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    var shopName:String?=""
    var token:String?=""
    var firabase_id:String?=""
    var tv_shop_name:TextView?=null
    var linear:LinearLayout?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_message_list, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        viewModel.pushListener=this
        shopName = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_SHOP_NAME)
        token = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
        tv_shop_name=root?.findViewById(R.id.tv_shop_name)
        linear=root?.findViewById(R.id.linear)
        tv_shop_name?.text=shopName
        viewModel.getFirebaseId(token!!,1,SharedPreferenceUtil.getShared(context!!, SharedPreferenceUtil.TYPE_SHOP_ID)!!)
        linear?.setOnClickListener {
            val intent = Intent(activity!!, MessageListActivity::class.java)
            intent.putExtra("userid", firabase_id)
            activity!!!!.startActivity(intent)
        }

        return root
    }

    override fun onLoad(data: String) {
        firabase_id=data
    }


}