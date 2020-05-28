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
import com.evan.dokan.data.db.entities.Product
import com.evan.dokan.ui.home.dashboard.product.IProductCategoryWiseUpdateListener
import com.evan.dokan.ui.home.wishlist.IWishListDeleteListener
import com.evan.dokan.ui.home.wishlist.WishListAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.layout_wish_list.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class OrderDetailsAdapter (val context: Context, val product: MutableList<Order>?, val productCategoryWiseUpdateListener: IProductCategoryWiseUpdateListener, val wishDeleteListener: IWishListDeleteListener) : RecyclerView.Adapter<OrderDetailsAdapter.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val cellForRow = inflater.inflate(R.layout.layout_wish_list, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        Log.e("getList",""+ Gson().toJson(product));

        return product!!.size
    }

    override fun onBindViewHolder(holder:CustomViewHolder, position: Int) {

//        holder.itemView.img_remove?.setOnClickListener {
//            wishDeleteListener?.onProduct(product?.get(position)!!)
//            product?.remove(product?.get(position)!!)
//        }
//
//        holder.itemView.setOnClickListener {
//            productCategoryWiseUpdateListener.onUpdate(product?.get(position)!!)
//        }
//        Glide.with(context)
//            .load(product?.get(position)?.ProductImage)
//            .into( holder.itemView.img_icon!!)
//
//        var discount_prices: String = ""
//        var total_prices: String = ""
//
//        holder.itemView.tv_date.text = getStartDate(product?.get(position)?.Date)
//        holder.itemView.text_name.text = product?.get(position)?.Name
//        holder.itemView.tv_stock.text = "Stock : " + product?.get(position)?.Stock+" "+product?.get(position)?.UnitName
//        if (product?.get(position)?.Discount == 0.0) {
//            holder.itemView.tv_product_discount_price.visibility = View.GONE
//            discount_prices =
//                "<b> <font color=#BF3E15>ট </font> : </b>" + product?.get(position)?.SellPrice.toString()
//            holder.itemView.tv_product_price.text = Html.fromHtml(discount_prices!!)
//        } else {
//            var discounts: Double = 0.0
//            discounts = product?.get(position)?.SellPrice!! - product?.get(position)?.Discount!!
//            discount_prices =
//                "<b> <font color=#BF3E15>ট </font> : </b>" + product?.get(position)?.SellPrice.toString()
//            total_prices = "<b> <font color=#BF3E15>ট </font> : </b>" + discounts
//            holder.itemView.tv_product_price.text = Html.fromHtml(discount_prices!!)
//            holder.itemView.tv_product_discount_price.text = Html.fromHtml(total_prices!!)
//            holder.itemView.tv_product_price.setPaintFlags( holder.itemView.tv_product_price.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
//        }


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