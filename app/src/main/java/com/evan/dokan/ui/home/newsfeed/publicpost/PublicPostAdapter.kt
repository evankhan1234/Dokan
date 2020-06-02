package com.evan.dokan.ui.home.newsfeed.publicpost

import android.content.Context
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evan.dokan.R
import com.evan.dokan.data.db.entities.Post
import com.evan.dokan.data.db.entities.Shop
import com.evan.dokan.ui.home.newsfeed.ownpost.IOwnPostUpdatedListener
import com.evan.dokan.ui.shop.IShopUpdateListener
import com.evan.dokan.util.is_like
import kotlinx.android.synthetic.main.layout_public_post_list.view.*

class PublicPostAdapter (val context: Context, val publicPostUpdateListener: IPublicPostUpdateListener,val publicPostLikeListener:IPublicPostLikeListener) :
    PagedListAdapter<Post, RecyclerView.ViewHolder>(NewsDiffCallback) {
    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AlertViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE)
            (holder as AlertViewHolder).bind(
                context,
                getItem(position),
                position,
                publicPostUpdateListener,
                publicPostLikeListener
            )
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    companion object {
        val NewsDiffCallback = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.Id == newItem.Id
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem == newItem
            }
        }
    }

}

class AlertViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(context: Context, post: Post?, position: Int, listener: IPublicPostUpdateListener,likeListener: IPublicPostLikeListener) {

        if (post != null) {

//            itemView.text_update.setOnClickListener {
//                listener.onUpdate(post)
//            }
            Glide.with(context)
                .load(post?.Image)
                .into(itemView.img_icon!!)
            Glide.with(context)
                .load(post?.Picture)
                .into(itemView.img_image!!)

            itemView.tv_content.text =post?.Content
            itemView.tv_name.text =post?.Name
            itemView.tv_count.text =post?.Love?.toString()
            if(post?.Type==2){
                itemView.tv_type.text ="Customer"
                itemView?.img_auth?.setImageDrawable(context?.getDrawable(R.drawable.correct))
            }
            else{
                itemView.tv_type.text ="ShopOwner"
                itemView?.img_auth?.setImageDrawable(context?.getDrawable(R.drawable.correct_blue))
            }
            itemView.img_like?.isSelected = post?.value

            //post?.value = post?.UserForId != null
            Log.e("datas","dats"+is_like);

            itemView.img_like?.setOnClickListener {
                if (!post?.value!!) {

                    itemView.img_like?.isSelected = true
                    post?.value = true
                    post.Love= post.Love!!+1
                    likeListener?.onCount(post.Love)

                } else {

                    itemView.img_like?.isSelected = false
                    post?.value = false
                    post.Love= post.Love!!-1
                    likeListener?.onCount(post.Love)
                }
            }

        }
    }

    companion object {
        fun create(parent: ViewGroup): AlertViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_public_post_list, parent, false)

            return AlertViewHolder(view)
        }
    }


}