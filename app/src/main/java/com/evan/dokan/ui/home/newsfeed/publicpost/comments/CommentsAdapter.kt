package com.evan.dokan.ui.home.newsfeed.publicpost.comments

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evan.dokan.R
import com.evan.dokan.data.db.entities.Comments
import com.evan.dokan.util.is_like
import com.google.gson.Gson
import kotlinx.android.synthetic.main.layout_comments_list.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class CommentsAdapter (val context: Context, val comments: MutableList<Comments>?, val commentsUpdateListener: ICommentsUpdateListener,val commentsPostLikeListener:ICommentsPostLikeListener) : RecyclerView.Adapter<CommentsAdapter.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val cellForRow = inflater.inflate(R.layout.layout_comments_list, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        Log.e("getList", "" + Gson().toJson(comments));

        return comments!!.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        holder.itemView.text_view.setOnClickListener {
            commentsUpdateListener.onShow(comments?.get(position)!!)
        }
        Glide.with(context)
            .load(comments?.get(position)?.UserImage).dontAnimate()
            .into(holder.itemView.img_icon!!)


        holder.itemView.tv_content.text =comments?.get(position)?.Content
        holder.itemView.tv_name.text =comments?.get(position)?.UserName
        holder.itemView.tv_count.text =comments?.get(position)?.Love?.toString()
        if(comments?.get(position)?.Type==2){
            holder.itemView.tv_type.text ="Customer"
            holder.itemView.img_auth?.setImageDrawable(context?.getDrawable(R.drawable.correct))
        }
        else if(comments?.get(position)?.Type==3){
            holder.itemView.tv_type.text ="Admin"
            holder.itemView.img_auth?.setImageDrawable(context?.getDrawable(R.drawable.admin_correct))
        }
        else{
            holder.itemView.tv_type.text ="ShopOwner"
            holder.itemView.img_auth?.setImageDrawable(context?.getDrawable(R.drawable.correct_blue))
        }
        holder.itemView.img_like?.isSelected = comments?.get(position)?.IsValue!!
        holder.itemView.img_like?.setOnClickListener {
            if (!comments?.get(position)?.IsValue!!) {

                holder.itemView.img_like?.isSelected = true
                comments?.get(position)?.IsValue = true
                comments?.get(position)?.Love= comments?.get(position)?.Love!!+1
                commentsPostLikeListener?.onCount(comments?.get(position)?.Love,1,comments?.get(position)?.Id!!)

            } else {

                holder.itemView.img_like?.isSelected = false
                comments?.get(position)?.IsValue = false
                comments?.get(position)?.Love= comments?.get(position)?.Love!!-1
                commentsPostLikeListener?.onCount(comments?.get(position)?.Love,2,comments?.get(position)?.Id!!)
            }
        }
        //post?.value = post?.UserForId != null
        Log.e("datas","dats"+ is_like);

//
        try {
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val past: Date = format.parse(comments?.get(position)?.Created)
            val currentDate = format.format(Date())
            val now: Date = format.parse(currentDate)

            val seconds: Long =
                TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime())
            val minutes: Long =
                TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime())
            val hours: Long =
                TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime())
            val days: Long = TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime())
            if (seconds < 60) {
                Log.e("ago","ago"+seconds+" seconds ago")
                holder.itemView.tv_hour.text=seconds.toString()+" seconds ago"
            } else if (minutes < 60) {
                println("$minutes minutes ago")
                Log.e("ago","ago"+minutes+" minutes ago")
                holder.itemView.tv_hour.text=minutes.toString()+" minutes ago"
            } else if (hours < 24) {
                println("$hours hours ago")
                Log.e("ago","ago"+hours+" hours ago")
                holder.itemView. tv_hour.text=hours.toString()+" hours ago"
            } else {
                println("$days days ago")
                Log.e("ago","ago"+days+" days ago")
                holder.itemView.tv_hour.text=days.toString()+"  days ago"
            }
        } catch (j: Exception) {
            j.printStackTrace()
        }


    }

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}