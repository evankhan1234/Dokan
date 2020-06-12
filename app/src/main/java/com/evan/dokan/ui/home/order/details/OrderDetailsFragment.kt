package com.evan.dokan.ui.home.order.details

import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.evan.dokan.R
import com.evan.dokan.data.db.entities.CustomerOrder
import com.evan.dokan.data.db.entities.Order
import com.evan.dokan.data.db.entities.OrderDetails
import com.evan.dokan.data.db.entities.Product
import com.evan.dokan.ui.home.HomeViewModel
import com.evan.dokan.ui.home.HomeViewModelFactory
import com.evan.dokan.ui.home.wishlist.WishListAdapter
import com.evan.dokan.util.SharedPreferenceUtil
import com.evan.dokan.util.hide
import com.evan.dokan.util.show
import com.google.gson.Gson
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class OrderDetailsFragment : Fragment(),KodeinAware,IOrderDetailsListener,ICustomerOrderListener {
    override val kodein by kodein()
    var progress_bar: ProgressBar?=null
    var rcv_orders: RecyclerView?=null
    private val factory : HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    var orderDetailsAdapter: OrderDetailsAdapter?=null
    var token:String?=""
    var order: Order?=null
    var tv_invoice:TextView?=null
    var tv_total:TextView?=null
    var tv_paid_amount:TextView?=null
    var tv_discount:TextView?=null
    var tv_order_details:TextView?=null
    var tv_delivery_charge:TextView?=null
    var tv_due_amount:TextView?=null
    var line1:CardView?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_order_details, container, false)

        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        viewModel.orderDetailsListener=this
        viewModel.customerOrderListener=this

        line1=root?.findViewById(R.id.line1)
        tv_invoice=root?.findViewById(R.id.tv_invoice)
        tv_total=root?.findViewById(R.id.tv_total)
        tv_paid_amount=root?.findViewById(R.id.tv_paid_amount)
        tv_discount=root?.findViewById(R.id.tv_discount)
        tv_order_details=root?.findViewById(R.id.tv_order_details)
        tv_delivery_charge=root?.findViewById(R.id.tv_delivery_charge)
        tv_due_amount=root?.findViewById(R.id.tv_due_amount)
        progress_bar=root?.findViewById(R.id.progress_bar)
        rcv_orders=root?.findViewById(R.id.rcv_orders)
        token = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
        val args: Bundle? = arguments
        if (args != null) {
            if (args?.containsKey(Product::class.java.getSimpleName()) != null) {
                order = args?.getParcelable(Order::class.java.getSimpleName())
            }

        }
        viewModel.getCustomerDetailsList(token!!,order?.Id!!.toInt())
        viewModel.getCustomerOrderInformation(token!!,order?.Id!!.toInt(),SharedPreferenceUtil.getShared(context!!, SharedPreferenceUtil.TYPE_SHOP_ID)!!.toInt())
        return root
    }

    override fun order(data: MutableList<OrderDetails>?) {
        orderDetailsAdapter = OrderDetailsAdapter(context!!,data!!)
        rcv_orders?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
            adapter = orderDetailsAdapter
        }
    }
    override fun onStarted() {

        progress_bar?.show()
    }

    override fun onEnd() {
        progress_bar?.hide()
    }

    override fun onShow(customerOrder: CustomerOrder?) {
        line1?.visibility=View.VISIBLE
        tv_invoice?.text=customerOrder?.InvoiceNumber
        tv_order_details?.text=customerOrder?.OrderDetails
        tv_delivery_charge?.text=customerOrder?.DeliveryCharge+" ট"
        tv_discount?.text=customerOrder?.Discount+" ট"
        tv_paid_amount?.text=customerOrder?.PaidAmount+" ট"
        tv_due_amount?.text=customerOrder?.DueAmount+" ট"
        tv_total?.text=customerOrder?.Total+" ট"
    }



    override fun onEmpty() {
        line1?.visibility=View.GONE
    }


}
