package com.evan.dokan.ui.home.settings.message

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evan.dokan.R
import com.evan.dokan.data.db.entities.Chat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MessageAdapter (val mContext: Context?, val mChat: List<Chat?>?, val imageurl: String?) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    val MSG_TYPE_LEFT = 0
    val MSG_TYPE_RIGHT = 1

    var fuser: FirebaseUser? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == MSG_TYPE_RIGHT) {
            val view: View =
                LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false)
            ViewHolder(view)
        } else {
            val view: View =
                LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false)
            ViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat: Chat = mChat!![position]!!
        holder.show_message.setText(chat.message)
        if (imageurl == "default") {
            holder.profile_image.setImageResource(R.mipmap.ic_launcher)
        } else {
            Glide.with(mContext!!).load(imageurl).into(holder.profile_image)
        }
        if (position == mChat.size - 1) {
            if (chat.isseen!!) {
                holder.txt_seen.setText("Seen")
            } else {
                holder.txt_seen.setText("Delivered")
            }
        } else {
            holder.txt_seen.setVisibility(View.GONE)
        }
    }

    override  fun getItemCount(): Int {
        return mChat!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var show_message: TextView
        var profile_image: ImageView
        var txt_seen: TextView

        init {
            show_message = itemView.findViewById(R.id.show_message)
            profile_image = itemView.findViewById(R.id.profile_image)
            txt_seen = itemView.findViewById(R.id.txt_seen)
        }
    }

    override  fun getItemViewType(position: Int): Int {
        fuser = FirebaseAuth.getInstance().currentUser
        return if (mChat!![position]!!.sender.equals(fuser!!.uid)) {
            MSG_TYPE_RIGHT
        } else {
            MSG_TYPE_LEFT
        }
    }
}