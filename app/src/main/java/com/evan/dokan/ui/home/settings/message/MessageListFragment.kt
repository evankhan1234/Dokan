package com.evan.dokan.ui.home.settings.message

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.evan.dokan.R
import com.evan.dokan.data.db.entities.Chat
import com.evan.dokan.data.db.entities.FIrebaseUser
import com.evan.dokan.ui.home.HomeViewModel
import com.evan.dokan.ui.home.HomeViewModelFactory
import com.evan.dokan.ui.home.cart.IPushListener
import com.evan.dokan.util.SharedPreferenceUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
    var tv_last_msg:TextView?=null
    var linear:LinearLayout?=null
    var profile_image:ImageView?=null
    var img_on:ImageView?=null
    var img_off:ImageView?=null
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
        tv_last_msg=root?.findViewById(R.id.tv_last_msg)
        profile_image=root?.findViewById(R.id.profile_image)
        linear=root?.findViewById(R.id.linear)
        img_on=root?.findViewById(R.id.img_on)
        img_off=root?.findViewById(R.id.img_off)
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
        lastMessage(firabase_id!!,tv_last_msg!!)
        val reference = FirebaseDatabase.getInstance().getReference("Users").child(firabase_id!!)
        reference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val user: FIrebaseUser? = dataSnapshot.getValue(FIrebaseUser::class.java)

                Glide.with(context!!)
                    .load(user?.imageURL)
                    .into(profile_image!!)
                if (user?.status.equals("online")) {
                    img_on?.setVisibility(View.VISIBLE)
                    img_off?.setVisibility(View.GONE)
                } else {
                    img_on?.setVisibility(View.GONE)
                    img_off?.setVisibility(View.VISIBLE)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
    var theLastMessage: String? = null
    open fun lastMessage(userid: String, last_msg: TextView) {

        theLastMessage = "default"
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val reference = FirebaseDatabase.getInstance().getReference("Chats")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val chat: Chat? = snapshot.getValue(Chat::class.java)
                    if (firebaseUser != null && chat != null) {
                        if (chat.receiver.equals(firebaseUser.uid) && chat.sender
                                .equals(userid) ||
                            chat.receiver.equals(userid) && chat.sender
                                .equals(firebaseUser.uid)
                        ) {
                            theLastMessage = chat.message
                            try {
                                if(chat.isseen!!){
                                    last_msg.setTextColor(context!!.resources.getColor(R.color.black_opacity_60))
                                }
                                else{
                                    last_msg.setTextColor(context!!.resources.getColor(R.color.colorPrimary))
                                }
                            } catch (e: Exception) {
                            }
                        }
                    }
                }
                when (theLastMessage) {
                    "default" -> last_msg.text = "No Message"
                    else -> last_msg.text = theLastMessage
                }
                theLastMessage = "default"
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

}