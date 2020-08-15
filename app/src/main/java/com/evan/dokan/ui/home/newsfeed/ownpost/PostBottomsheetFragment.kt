package com.evan.dokan.ui.home.newsfeed.ownpost

import android.app.Dialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.evan.dokan.R
import com.evan.dokan.ui.home.HomeActivity
import com.bumptech.glide.request.target.Target
import com.evan.dokan.data.db.entities.Post

import com.evan.dokan.ui.home.HomeViewModel
import com.evan.dokan.ui.home.HomeViewModelFactory
import com.evan.dokan.ui.home.newsfeed.publicpost.PublicPostAdapter
import com.evan.dokan.ui.home.newsfeed.publicpost.PublicPostModelFactory
import com.evan.dokan.ui.home.newsfeed.publicpost.PublicPostViewModel
import com.evan.dokan.util.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_shop.*
import kotlinx.android.synthetic.main.layout_own_post_list.view.*
import kotlinx.android.synthetic.main.layout_shop_list.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*


class PostBottomsheetFragment(posts: Post?) : BottomSheetDialogFragment(),KodeinAware,IPostListener {
    private var mBehavior: BottomSheetBehavior<*>? = null
    override val kodein by kodein()
    var post: Post?=posts
    private val factory : HomeViewModelFactory by instance()



    private lateinit var viewModel: HomeViewModel
    var img_image:ImageView?=null
    var root_layout:RelativeLayout?=null
    var img_dismiss:ImageView?=null
    var progress_bar:ProgressBar?=null
    var img_image_cross:ImageView?=null
    var linear_photo:LinearLayout?=null
    var et_content:EditText?=null
    var btn_ok:Button?=null
    var image_address: String?="empty"
    var token:String?=""
    var name:String?=""
    var image:String?=""
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =
            super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        val view =
            View.inflate(context, R.layout.fragment_post_bottosheet, null)

        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        viewModel.postListener=this
        img_image=view?.findViewById(R.id.img_image)
        root_layout=view?.findViewById(R.id.root_layout)
        progress_bar=view?.findViewById(R.id.progress_bar)
        et_content=view?.findViewById(R.id.et_content)
        btn_ok=view?.findViewById(R.id.btn_ok)
        linear_photo=view?.findViewById(R.id.linear_photo)
        img_image_cross=view?.findViewById(R.id.img_image_cross)
        img_dismiss=view?.findViewById(R.id.img_dismiss)
        dialog.setContentView(view)
        mBehavior =
            BottomSheetBehavior.from(view.parent as View)
        if(post!=null){
            image_address=post?.Picture
            et_content?.setText(post?.Content)
            if(!image_address.equals("empty")){
                Log.e("picture","picture"+image_address)
                img_image?.visibility=View.VISIBLE
                Glide.with(this)
                    .load(post?.Picture).dontAnimate()
                    .into(img_image!!)
            }

        }
        img_dismiss?.setOnClickListener {
            dismiss()
            (activity as HomeActivity?)!!.onBottomBackPress()
        }
        img_image_cross?.setOnClickListener {
            image_address="empty"
            img_image?.visibility=View.GONE
            img_image_cross?.visibility=View.GONE
        }
        linear_photo?.setOnClickListener{
            image_update ="post"
            (activity as HomeActivity?)!!.openImageChooser()
        }
        token = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
        name = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_NAME)
        image = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_IMAGE)
        btn_ok?.setOnClickListener {
            var content:String=""
            content=et_content?.text.toString()
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val currentDate = sdf.format(Date())

            Log.e("name","name"+name)
            Log.e("content","content"+content)
            Log.e("image_address","image_address"+image_address)
            Log.e("currentDate","currentDate"+currentDate)
            Log.e("image","image"+image)
            if(content?.isNullOrEmpty()){
                Toast.makeText(context!!,"Please Enter your thoughts",Toast.LENGTH_SHORT).show()
            }
            else{
                if(post==null){
                    viewModel?.createdNewsFeedPost(token!!,name!!,content,image_address!!,currentDate,0,2,image!!,0)
                }
                else{
                    viewModel?.updateNewsFeedPost(token!!,post?.Id!!,name!!,content,image_address!!,2,image!!)

                }
            }
        }
        return dialog
    }
    override fun onStart() {
        super.onStart()
        mBehavior?.setState(BottomSheetBehavior.STATE_EXPANDED)
    }
    fun showImage(temp:String?){
        img_image?.visibility=View.VISIBLE
        img_image_cross?.visibility=View.VISIBLE
        image_address="http://hathbazzar.com/"+temp
        Log.e("for","Image"+temp)
//        Glide.with(this)
//            .load("http://192.168.0.106/"+temp)
//            .into(img_image!!)
        loadImage("http://hathbazzar.com/"+temp)
    }
    fun loadImage(image_path:String){
        progress_bar?.visibility = View.VISIBLE
        Glide.with(this)
            .load(image_path)
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progress_bar?.visibility = View.GONE
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progress_bar?.visibility = View.GONE
                    return false
                }
            })
            .into(img_image!!)
    }
    override fun onStarted() {
        progress_bar?.show()

    }

    override fun onSuccess(message: String) {
        Toast.makeText(context!!,message,Toast.LENGTH_SHORT).show()
        dismiss()
        (activity as HomeActivity?)!!.onBottomBackPress()
    }

    override fun onFailure(message: String) {
        Toast.makeText(context!!,message,Toast.LENGTH_SHORT).show()
    }

    override fun onEnd() {
        progress_bar?.hide()
    }
}