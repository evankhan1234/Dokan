package com.evan.dokan.ui.home.order.details

import android.content.Context
import android.graphics.Paint
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evan.dokan.R
import com.evan.dokan.data.db.entities.Order
import com.evan.dokan.data.db.entities.OrderDetails
import com.evan.dokan.data.db.entities.Product
import com.evan.dokan.ui.home.dashboard.product.IProductCategoryWiseUpdateListener
import com.evan.dokan.ui.home.wishlist.IWishListDeleteListener
import com.evan.dokan.ui.home.wishlist.WishListAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.layout_order_details_list.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class OrderDetailsAdapter (val context: Context, val product: MutableList<OrderDetails>?) : RecyclerView.Adapter<OrderDetailsAdapter.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val cellForRow = inflater.inflate(R.layout.layout_order_details_list, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        Log.e("getList",""+ Gson().toJson(product));

        return product!!.size
    }

    override fun onBindViewHolder(holder:CustomViewHolder, position: Int) {


        Glide.with(context)
            .load(product?.get(position)?.Picture)
            .into( holder.itemView.img_icon!!)

        var prices: String = ""
        var quantity: String = ""

        holder.itemView.tv_date.text = getStartDate(product?.get(position)?.Created)
        holder.itemView.text_name.text = product?.get(position)?.Name
        prices = "<b> <font color=#BF3E15>Price ট </font> : </b>" + product?.get(position)?.Price.toString()
        quantity = "<b> <font color=#BF3E15>Quantity  </font> : </b>" + product?.get(position)?.Quantity.toString()
        holder.itemView.tv_product_price.text = Html.fromHtml(prices!!)
        holder.itemView.tv_quantity.text = Html.fromHtml(quantity!!)



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