package com.evan.dokan.ui.home.order

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
import com.evan.dokan.data.db.entities.Order
import kotlinx.android.synthetic.main.layout_order_list.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class OrderAdapter (val context: Context, val orderUpdateListener: IOrderUpdateListener) :
    PagedListAdapter<Order, RecyclerView.ViewHolder>(NewsDiffCallback) {
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
                orderUpdateListener
            )
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    companion object {
        val NewsDiffCallback = object : DiffUtil.ItemCallback<Order>() {
            override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
                return oldItem.Id == newItem.Id
            }

            override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
                return oldItem == newItem
            }
        }
    }

}

class AlertViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(context: Context, order: Order?, position: Int, listener: IOrderUpdateListener) {

        if (order != null) {
            itemView.text_view.setOnClickListener {
                listener.onUpdate(order!!)
            }
            Glide.with(context)
                .load(order?.Picture)
                .into(itemView.img_icon!!)
           itemView.text_name.setText(order?.Name)
            itemView.text_phone_number.setText(order?.MobileNumber)
            itemView.text_email.setText(order?.Email)
            var order_address:String=""
            var order_area:String=""

            order_address = "<b> <font color=#15507E>Order Address</font> : </b>"+order?.OrderAddress
            order_area = "<b> <font color=#15507E>Order Area </font> : </b>"+order?.OrderArea
            itemView.tv_order_address.text= Html.fromHtml(order_address)
            itemView.tv_order_area.text= Html.fromHtml(order_area)
            itemView.tv_date.text=getStartDate(order?.Created)

        }
    }

    companion object {
        fun create(parent: ViewGroup): AlertViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_order_list, parent, false)

            return AlertViewHolder(view)
        }
    }
    fun getStartDate(startDate: String?): String? {
        val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val formatter = SimpleDateFormat("dd,MMMM yyyy")
        val output: String = formatter.format(parser.parse(startDate!!))
        return output
    }

}