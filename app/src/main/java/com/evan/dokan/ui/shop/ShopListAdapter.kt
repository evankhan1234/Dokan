package com.evan.dokan.ui.shop

import android.content.Context
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evan.dokan.R
import com.evan.dokan.data.db.entities.Shop
import com.google.gson.Gson
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlinx.android.synthetic.main.layout_shop_list.view.*
class ShopListAdapter(val context: Context, val shop: MutableList<Shop>?, val iShopUpdateListener: IShopUpdateListener) : RecyclerView.Adapter<ShopListAdapter.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val cellForRow = inflater.inflate(R.layout.layout_shop_list, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        Log.e("getList",""+ Gson().toJson(shop));

        return shop!!.size
    }

    override fun onBindViewHolder(holder:CustomViewHolder, position: Int) {


        holder.itemView.text_update.setOnClickListener {
            iShopUpdateListener.onUpdate(shop?.get(position)!!)
        }
        Glide.with(context)
            .load("https://img.webmd.com/dtmcms/live/webmd/consumer_assets/site_images/article_thumbnails/slideshows/powerhouse_vegetables_slideshow/650x350_powerhouse_vegetables_slideshow.jpg")
            .into(holder.itemView.img_image!!)

        var shop_name: String = ""
        var shop_address: String = ""

        shop_name = "<b> <font color=#15507E>Shop Name</font> : </b>" + shop?.get(position)?.Name
        shop_address = "<b> <font color=#15507E>Shop Address</font> : </b>" + shop?.get(position)?.Address
        holder.itemView.tv_shop_name.text = Html.fromHtml(shop_name)
        holder.itemView.tv_shop_address.text = Html.fromHtml(shop_address)


    }
    fun getStartDate(startDate: String?): String? {
        val inputFormat =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val outputFormat =
            DateTimeFormatter.ofPattern("dd,MMMM yyyy")
        return LocalDate.parse(startDate, inputFormat).format(outputFormat)
    }
    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }
}