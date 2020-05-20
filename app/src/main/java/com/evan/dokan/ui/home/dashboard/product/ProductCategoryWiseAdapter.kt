package com.evan.dokan.ui.home.dashboard.product

import android.content.Context
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
import com.evan.dokan.ui.shop.IShopUpdateListener
import kotlinx.android.synthetic.main.layout_product_list.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ProductCategoryWiseAdapter (val context: Context, val productCategoryWiseUpdateListener: IProductCategoryWiseUpdateListener) :
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

    fun bind(context: Context, product: Product?, position: Int, listener: IProductCategoryWiseUpdateListener) {

        if (product != null) {

//            itemView.text_update.setOnClickListener {
//                listener.onUpdate(product)
//            }
            Glide.with(context)
                .load(product?.ProductImage)
                .into(itemView.img_image!!)
//
//            var shop_name: String = ""
//            var shop_address: String = ""
//
//            shop_name = "<b> <font color=#15507E>Shop Name</font> : </b>" + shop?.Name
//            shop_address = "<b> <font color=#15507E>Shop Address</font> : </b>" + shop?.Address
//            itemView.tv_shop_name.text = Html.fromHtml(shop_name)
//            itemView.tv_shop_address.text = Html.fromHtml(shop_address)


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