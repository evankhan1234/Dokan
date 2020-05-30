package com.evan.dokan.ui.home.dashboard.product

import android.content.Context
import android.graphics.Paint
import android.opengl.Visibility
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evan.dokan.R
import com.evan.dokan.data.db.entities.Product
import kotlinx.android.synthetic.main.layout_product_list.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ProductCategoryWiseAdapter(
    val context: Context,
    val productCategoryWiseUpdateListener: IProductCategoryWiseUpdateListener
) :
    PagedListAdapter<Product, RecyclerView.ViewHolder>(NewsDiffCallback) {
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
                productCategoryWiseUpdateListener
            )
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    companion object {
        val NewsDiffCallback = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.Id == newItem.Id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }
    }

}

class AlertViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(
        context: Context,
        product: Product?,
        position: Int,
        listener: IProductCategoryWiseUpdateListener
    ) {

        if (product != null) {

            itemView.setOnClickListener {
                listener.onUpdate(product)
            }
            Glide.with(context)
                .load(product?.ProductImage)
                .into(itemView.img_image!!)

            var discount_prices: String = ""
            var total_prices: String = ""

            itemView.tv_date.text = getStartDate(product?.Created)
            itemView.tv_product_name.text = product?.Name
            itemView.tv_stock.text = "Stock : " + product?.Stock+" "+product?.UnitName
            if (product?.Discount == 0.0) {
                itemView.tv_product_discount_price.visibility = View.GONE
                discount_prices =
                    "<b> <font color=#BF3E15>ট </font> : </b>" + product?.SellPrice.toString()
                itemView.tv_product_price.text = Html.fromHtml(discount_prices!!)
            } else {
                var discounts: Double = 0.0
                discounts = product?.SellPrice!! - product?.Discount!!
                val number2digitsub: Double = String.format("%.2f", discounts).toDouble()
                discount_prices =
                    "<b> <font color=#BF3E15>ট </font> : </b>" + product?.SellPrice.toString()
                total_prices = "<b> <font color=#BF3E15>ট </font> : </b>" + number2digitsub
                itemView.tv_product_price.text = Html.fromHtml(discount_prices!!)
                itemView.tv_product_discount_price.text = Html.fromHtml(total_prices!!)
                itemView.tv_product_price.setPaintFlags(itemView.tv_product_price.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
            }


        }
    }

    companion object {
        fun create(parent: ViewGroup): AlertViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_product_list, parent, false)

            return AlertViewHolder(view)
        }
    }

    fun getStartDate(startDate: String?): String? {
        val inputFormat =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val outputFormat =
            DateTimeFormatter.ofPattern("dd,MMMM yyyy")
        return LocalDate.parse(startDate, inputFormat).format(outputFormat)
    }

}