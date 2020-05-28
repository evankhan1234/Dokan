package com.evan.dokan.ui.home.order.details

import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.evan.dokan.R
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


class OrderDetailsFragment : Fragment(),KodeinAware,IOrderDetailsListener {
    override val kodein by kodein()
    var progress_bar: ProgressBar?=null
    var rcv_orders: RecyclerView?=null
    private val factory : HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    var orderDetailsAdapter: OrderDetailsAdapter?=null
    var token:String?=""
    var order: Order?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_order_details, container, false)

        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        viewModel.orderDetailsListener=this

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


}
