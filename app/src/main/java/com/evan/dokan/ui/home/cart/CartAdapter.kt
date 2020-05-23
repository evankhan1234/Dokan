package com.evan.dokan.ui.home.cart

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
import com.evan.dokan.data.db.entities.Cart
import com.evan.dokan.data.db.entities.Product
import com.evan.dokan.ui.home.dashboard.product.IProductCategoryWiseUpdateListener
import com.evan.dokan.ui.home.wishlist.IWishListDeleteListener
import com.evan.dokan.ui.home.wishlist.WishListAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.layout_cart_list.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CartAdapter (val context: Context, val cart: MutableList<Cart>?, val cartQuantityListener: ICartQuantityListener, val cardDeleteListener: ICartDeleteListener,val cartItemClickListener:ICartItemClickListener) : RecyclerView.Adapter<CartAdapter.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val cellForRow = inflater.inflate(R.layout.layout_cart_list, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        Log.e("getList",""+ Gson().toJson(cart));

        return cart!!.size
    }

    override fun onBindViewHolder(holder:CustomViewHolder, position: Int) {
        holder.itemView.img_remove?.setOnClickListener {
            cardDeleteListener?.onCart(cart?.get(position)!!)
            cart?.remove(cart?.get(position)!!)
            cartItemClickListener?.onPosition(position)
        }
        holder.itemView.img_plus?.setOnClickListener {

            cart?.get(position)?.Quantity= cart?.get(position)?.Quantity!!+1
            cart?.set(position,cart?.get(position))
            notifyDataSetChanged()
            cartQuantityListener?.onQuantity(cart?.get(position)?.Quantity!!,position,cart?.get(position)?.ProductId!!)
        }
        holder.itemView.img_minus.setOnClickListener {
            cart?.get(position)?.Quantity= cart?.get(position)?.Quantity!!-1
            if(cart?.get(position)?.Quantity!!<1){
                cart?.get(position)?.Quantity=1
            }
            else{
                cart?.set(position,cart?.get(position))
            }
            cartQuantityListener?.onQuantity(cart?.get(position)?.Quantity!!,position,cart?.get(position)?.ProductId!!)
            notifyDataSetChanged()
        }
        Glide.with(context)
            .load(cart?.get(position)?.Picture)
            .into( holder.itemView.img_icon!!)

        var prices: String = ""
        var total_prices: String = ""
        holder.itemView.tv_date.text = getStartDate(cart?.get(position)?.Created)
        holder.itemView.text_name.text = cart?.get(position)?.Name
        holder.itemView.text_quantity.text = cart?.get(position)?.Quantity.toString()
        var total: Double = 0.0
        total = cart?.get(position)?.Price!! * cart?.get(position)?.Quantity!!
        prices =
            "<b> <font color=#BF3E15>ট </font> : </b>" + cart?.get(position)?.Price.toString()+" * "+ cart?.get(position)?.Quantity.toString()
        total_prices = "<b> <font color=#BF3E15>ট </font> : </b>" + total
        holder.itemView.tv_product_price.text = Html.fromHtml(prices!!)
        holder.itemView.tv_product_total_price.text = Html.fromHtml(total_prices!!)


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