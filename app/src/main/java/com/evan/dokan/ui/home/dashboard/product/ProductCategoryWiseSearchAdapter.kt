package com.evan.dokan.ui.home.dashboard.product

import android.content.Context
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evan.dokan.R
import com.evan.dokan.data.db.entities.Product
import com.evan.dokan.data.db.entities.Shop
import com.evan.dokan.ui.shop.IShopUpdateListener
import com.evan.dokan.ui.shop.ShopListAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.layout_shop_list.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ProductCategoryWiseSearchAdapter(val context: Context, val product: MutableList<Product>?, val productCategoryWiseUpdateListener: IProductCategoryWiseUpdateListener) : RecyclerView.Adapter<ProductCategoryWiseSearchAdapter.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val cellForRow = inflater.inflate(R.layout.layout_product_list, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        Log.e("getList",""+ Gson().toJson(product));

        return product!!.size
    }

    override fun onBindViewHolder(holder:CustomViewHolder, position: Int) {


//        holder.itemView.text_update.setOnClickListener {
//            productCategoryWiseUpdateListener.onUpdate(product?.get(position)!!)
//        }
//        Glide.with(context)
//            .load("https://img.webmd.com/dtmcms/live/webmd/consumer_assets/site_images/article_thumbnails/slideshows/powerhouse_vegetables_slideshow/650x350_powerhouse_vegetables_slideshow.jpg")
//            .into(holder.itemView.img_image!!)
//
//        var shop_name: String = ""
//        var shop_address: String = ""
//
//        shop_name = "<b> <font color=#15507E>Shop Name</font> : </b>" + shop?.get(position)?.Name
//        shop_address = "<b> <font color=#15507E>Shop Address</font> : </b>" + shop?.get(position)?.Address
//        holder.itemView.tv_shop_name.text = Html.fromHtml(shop_name)
//        holder.itemView.tv_shop_address.text = Html.fromHtml(shop_address)


    }

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }
}