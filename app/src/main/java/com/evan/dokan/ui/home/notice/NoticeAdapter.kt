package com.evan.dokan.ui.home.notice

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
import com.evan.dokan.data.db.entities.Notice
import com.evan.dokan.ui.shop.IShopUpdateListener
import kotlinx.android.synthetic.main.fragment_notice_view.view.*
import kotlinx.android.synthetic.main.layout_notice_list.view.*
import kotlinx.android.synthetic.main.layout_notice_list.view.img_image
import kotlinx.android.synthetic.main.layout_notice_list.view.tv_content
import kotlinx.android.synthetic.main.layout_notice_list.view.tv_date
import kotlinx.android.synthetic.main.layout_notice_list.view.tv_title
import kotlinx.android.synthetic.main.layout_product_list.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NoticeAdapter (val context: Context, val noticeUpdateListener: INoticeUpdateListener) :
    PagedListAdapter<Notice, RecyclerView.ViewHolder>(NewsDiffCallback) {
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
                noticeUpdateListener
            )
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    companion object {
        val NewsDiffCallback = object : DiffUtil.ItemCallback<Notice>() {
            override fun areItemsTheSame(oldItem: Notice, newItem: Notice): Boolean {
                return oldItem.Id == newItem.Id
            }

            override fun areContentsTheSame(oldItem: Notice, newItem: Notice): Boolean {
                return oldItem == newItem
            }
        }
    }

}

class AlertViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(context: Context, notice: Notice?, position: Int, listener: INoticeUpdateListener) {

        if (notice != null) {

            itemView.text_update.setOnClickListener {
                listener.onUpdate(notice)
            }
            Glide.with(context)
                .load(notice?.Image)
                .into(itemView.img_image!!)

            var shop_name: String = ""
            var shop_address: String = ""

            shop_name = "<b> <font color=#BF3E15>Title</font> : </b>" + notice?.Title
            shop_address = "<b> <font color=#BF3E15>Content</font> : </b>" + notice?.Content
            itemView.tv_title.text = Html.fromHtml(shop_name)
            itemView.tv_content.text = Html.fromHtml(shop_address)
            itemView.tv_date.text = getStartDate(notice?.Created)

        }
    }

    companion object {
        fun create(parent: ViewGroup): AlertViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_notice_list, parent, false)

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