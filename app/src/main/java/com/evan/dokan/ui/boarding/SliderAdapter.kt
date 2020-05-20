package com.evan.bazar.ui.boarding

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.evan.dokan.R
import com.bumptech.glide.request.target.Target

import com.google.gson.Gson
import kotlinx.android.synthetic.main.layout_slide_item_containter.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SliderAdapter (val context: Context, val order: List<IntroSlide>?) : RecyclerView.Adapter<SliderAdapter.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val cellForRow = inflater.inflate(R.layout.layout_slide_item_containter, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        Log.e("getList",""+ Gson().toJson(order));

        return order!!.size
    }

    override fun onBindViewHolder(holder:CustomViewHolder, position: Int) {


        holder.itemView.progress_circular?.visibility = View.VISIBLE
        Glide.with(context)
            .load(order?.get(position)?.icon)
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.itemView.progress_circular?.visibility = View.GONE
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.itemView.progress_circular?.visibility = View.GONE
                    return false
                }
            })
            .into(holder.itemView.img_name!!)
        holder.itemView.tv_title.setText(order?.get(position)?.title)
        holder.itemView.tv_description.setText(order?.get(position)?.description)


    }
    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }
}