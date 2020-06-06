package com.evan.dokan.ui.home.newsfeed.publicpost.reply

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evan.dokan.R
import com.evan.dokan.data.db.entities.Comments
import com.evan.dokan.data.db.entities.Reply
import com.evan.dokan.ui.home.newsfeed.publicpost.comments.CommentsAdapter
import com.evan.dokan.ui.home.newsfeed.publicpost.comments.ICommentsPostLikeListener
import com.evan.dokan.ui.home.newsfeed.publicpost.comments.ICommentsUpdateListener
import com.evan.dokan.util.is_like
import com.google.gson.Gson
import kotlinx.android.synthetic.main.layout_reply_list.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class ReplyAdapter (val context: Context, val reply: MutableList<Reply>?) : RecyclerView.Adapter<ReplyAdapter.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val cellForRow = inflater.inflate(R.layout.layout_reply_list, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        Log.e("getList", "" + Gson().toJson(reply));

        return reply!!.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        Glide.with(context)
            .load(reply?.get(position)?.UserImage).dontAnimate()
            .into(holder.itemView.img_icon!!)


        holder.itemView.tv_content.text =reply?.get(position)?.Content
        holder.itemView.tv_name.text =reply?.get(position)?.Username

        if(reply?.get(position)?.Type==2){
            holder.itemView.tv_type.text ="Customer"
            holder.itemView.img_auth?.setImageDrawable(context?.getDrawable(R.drawable.correct))
        }
        else{
            holder.itemView.tv_type.text ="ShopOwner"
            holder.itemView.img_auth?.setImageDrawable(context?.getDrawable(R.drawable.correct_blue))
        }

        //post?.value = post?.UserForId != null
        Log.e("datas","dats"+ is_like);

        try {
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val past: Date = format.parse(reply?.get(position)?.Created)
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