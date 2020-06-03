package com.evan.dokan.ui.home.newsfeed.ownpost

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evan.dokan.R
import com.evan.dokan.data.db.entities.Post

import com.evan.dokan.ui.shop.IShopUpdateListener

import kotlinx.android.synthetic.main.layout_own_post_list.view.*


class OwnPostAdapter (val context: Context, val ownPostUpdatedListener: IOwnPostUpdatedListener) :
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
                ownPostUpdatedListener
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

    fun bind(context: Context, post: Post?, position: Int, listener: IOwnPostUpdatedListener) {

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

        }
    }

    companion object {
        fun create(parent: ViewGroup): AlertViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_own_post_list, parent, false)

            return AlertViewHolder(view)
        }
    }


}