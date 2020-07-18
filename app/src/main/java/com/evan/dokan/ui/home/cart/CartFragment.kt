package com.evan.dokan.ui.home.cart

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evan.bazar.ui.custom.CustomDialog

import com.evan.dokan.R
import com.evan.dokan.data.db.entities.Cart
import com.evan.dokan.data.db.entities.Shop
import com.evan.dokan.data.network.post.*
import com.evan.dokan.ui.home.HomeActivity
import com.evan.dokan.ui.home.HomeViewModel
import com.evan.dokan.ui.home.HomeViewModelFactory
import com.evan.dokan.ui.home.wishlist.WishListAdapter
import com.evan.dokan.util.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.layout_cart_list.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CartFragment : Fragment() ,KodeinAware,ICartListListener,ICartDeleteListener,ICartQuantityListener,ICartListDeleteListener,ICartItemClickListener,IPushListener,IShopListener{

    override val kodein by kodein()
    var progress_bar: ProgressBar?=null
    var tv_total: TextView?=null
    var rcv_cart: RecyclerView?=null
    var btn_ok: Button?=null
    private val factory : HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    var cartAdapter: CartAdapter?=null
    var token:String?=""
    var cart_list: MutableList<Cart>?=null

    var customerOrderPost:CustomerOrderPost?=null
    var data_customer_status: MutableList<OrderPost>? = null
    var list: ArrayList<OrderPost> = arrayListOf()

    var ima_no_data:ImageView?=null
    var root_layout:RelativeLayout?=null
    var data_cart_order: MutableList<CartOrderPost>? = null
    var list_cart_order: ArrayList<CartOrderPost> = arrayListOf()
    var pushPost: PushPost?=null
    var push: Push?=null

    var latitude:Double?=0.0
    var longitude:Double?=0.0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_cart, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        viewModel.cartListListener=this
        viewModel.cartListDeleteListener=this
        viewModel.pushListener=this
        viewModel.shopListeners=this
        root_layout=root?.findViewById(R.id.root_layout)
        progress_bar=root?.findViewById(R.id.progress_bar)
        ima_no_data=root?.findViewById(R.id.ima_no_data)
        btn_ok=root?.findViewById(R.id.btn_ok)
        tv_total=root?.findViewById(R.id.tv_total)
        rcv_cart=root?.findViewById(R.id.rcv_cart)
        token = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
        viewModel.getCartList(token!!,
            SharedPreferenceUtil.getShared(context!!, SharedPreferenceUtil.TYPE_SHOP_ID)!!.toInt())

        viewModel.getToken(token!!,1,SharedPreferenceUtil.getShared(context!!, SharedPreferenceUtil.TYPE_SHOP_ID)!!)

        viewModel.getShopBy(token!!,SharedPreferenceUtil.getShared(context!!, SharedPreferenceUtil.TYPE_SHOP_ID)!!.toInt())
        btn_ok?.setOnClickListener {

            showDialog(context!!)
        }
        //onData()
        return root
    }

    fun onData(){
        if (cart_list?.size!!<1){
            ima_no_data?.visibility=View.VISIBLE
            root_layout?.visibility=View.GONE
            Glide.with(context!!)
                .load("https://financialadvisors.com/media/no-images/nodata-found.png")
                .into( ima_no_data!!)
        }
        else{
            ima_no_data?.visibility=View.GONE
            root_layout?.visibility=View.VISIBLE
        }
    }
    override fun cart(data: MutableList<Cart>?) {


        cart_list=data
        onData()
        cartAdapter = CartAdapter(context!!,data!!,this,this,this)
        rcv_cart?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
            adapter = cartAdapter
        }
        var price: Double? = 0.0
        for (i in data?.indices!!) {
            price = price!! + (data?.get(i).Price!! * data?.get(i).Quantity!!)
            var dataa = OrderPost(data?.get(i).Name!!, data?.get(i).Quantity!!,data?.get(i).Price!!, data?.get(i).ProductId!!,1,SharedPreferenceUtil.getShared(context!!, SharedPreferenceUtil.TYPE_SHOP_ID)!!.toInt(),data?.get(i).Picture!!,data?.get(i).Created!!)
            var data_cart=CartOrderPost(SharedPreferenceUtil.getShared(context!!, SharedPreferenceUtil.TYPE_SHOP_ID)!!.toInt(),data?.get(i).ProductId!!,2)
            list_cart_order?.add(data_cart)
            list?.add(dataa)
        }
        tv_total?.text="Total: "+price.toString()


    }

    override fun onSuccess(message: String) {
        Toast.makeText(context!!,message, Toast.LENGTH_SHORT).show()
        if (activity is HomeActivity) {
            (activity as HomeActivity).onCount()
        }
    }

    override fun onStarted() {

        progress_bar?.show()
    }

    override fun onEnd() {
        progress_bar?.hide()
    }

    override fun onFailure(message: String) {
        Toast.makeText(context!!,message, Toast.LENGTH_SHORT).show()
    }

    override fun onQuantity(quantity: Int,position:Int,productId:Int) {
        list?.clear()
        list_cart_order?.clear()
        viewModel.updateCartQuantity(token!!,SharedPreferenceUtil.getShared(context!!, SharedPreferenceUtil.TYPE_SHOP_ID)!!.toInt(),productId,quantity)
        cart_list?.get(position)?.Quantity= quantity
        cart_list?.set(position, cart_list?.get(position)!!)
        var price: Double? = 0.0
        for (i in cart_list?.indices!!) {
            price = price!! + (cart_list?.get(i)!!.Price!! * cart_list?.get(i)!!.Quantity!!)
            var dataa = OrderPost(cart_list?.get(i)!!.Name!!, cart_list?.get(i)!!.Quantity!!,cart_list?.get(i)!!.Price!!, cart_list?.get(i)!!.ProductId!!,1,SharedPreferenceUtil.getShared(context!!, SharedPreferenceUtil.TYPE_SHOP_ID)!!.toInt(),cart_list?.get(i)!!.Picture!!,cart_list?.get(i)!!.Created!!)
            var data_cart=CartOrderPost(SharedPreferenceUtil.getShared(context!!, SharedPreferenceUtil.TYPE_SHOP_ID)!!.toInt(),cart_list?.get(i)!!.ProductId!!,2)
            list_cart_order?.add(data_cart)
            list?.add(dataa)
        }
        tv_total?.text="Total: "+price.toString()
    }

    fun showDialog(mContext: Context) {
        val infoDialog = CustomDialog(mContext, R.style.CustomDialogTheme)
        val inflator =
            mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v: View = inflator.inflate(R.layout.layout_place_order_status, null)
        infoDialog.setContentView(v)
        infoDialog.getWindow()?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val btnOK = infoDialog.findViewById(R.id.btn_ok) as Button
        val linear_delivery = infoDialog.findViewById(R.id.linear_delivery) as LinearLayout
        val btn_success = infoDialog.findViewById(R.id.btn_success) as Button
        val btn_cancel = infoDialog.findViewById(R.id.btn_cancel) as Button
        val et_order_address = infoDialog.findViewById(R.id.et_order_address) as EditText
        val et_order_area = infoDialog.findViewById(R.id.et_order_area) as EditText

        btnOK.setOnClickListener {

            var order_address: String? = ""
            var order_area: String? = ""
            order_address=et_order_address?.text.toString()
            order_area=et_order_area?.text.toString()
            if(order_area.isNullOrEmpty() && order_address.isNullOrEmpty() ){
                Toast.makeText(mContext,"All fields Empty",Toast.LENGTH_SHORT).show()
            }
            else if(order_area.isNullOrEmpty()){
                Toast.makeText(mContext,"Order Details fields Empty",Toast.LENGTH_SHORT).show()
            }
            else if(order_address.isNullOrEmpty()){
                Toast.makeText(mContext,"Order Area fields Empty",Toast.LENGTH_SHORT).show()
            }
            else{


                Log.e("delivery_charge","delivery_charge"+order_address)
                data_customer_status=list
                data_cart_order=list_cart_order
                val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                val currentDate = sdf.format(Date())
                customerOrderPost= CustomerOrderPost(SharedPreferenceUtil.getShared(context!!, SharedPreferenceUtil.TYPE_SHOP_ID)!!.toInt(),1,currentDate,order_address,order_area,latitude!!,longitude!!,data_customer_status!!)
                Log.e("data_customer_status","data_customer_status"+Gson().toJson(customerOrderPost))
                viewModel.postOrderList(token!!,customerOrderPost!!)
                viewModel.updateCartOrderDetails(token!!,data_cart_order)


                    showDialogSuccessfull(context!!,
                        "SuccessFully Place Order",
                        "Ok",
                        "Ok",
                        object :
                            DialogActionListener {
                            override fun onPositiveClick() {
                                cartAdapter?.notifyDataSetChanged()
                                cart_list?.clear()
                                push= Push("Orders","You have received a new Order")
                                pushPost= PushPost(tokenData,push)
                                viewModel.sendPush("key=AAAAdCyJ2hw:APA91bGF6x20oQnuC2ZeAXsJju-OCAZ67dBpQvaLx7h18HSAnhl9CPWupCJaV0552qJvm1qIHL_LAZoOvv5oWA9Iraar_XQkWe3JEUmJ1v7iKq09QYyPB3ZGMeSinzC-GlKwpaJU_IvO",pushPost!!)
                                onData()
                                if (activity is HomeActivity) {
                                    (activity as HomeActivity).onCount()
                                }
                            }

                            override fun onNegativeClick() {

                            }
                        })
                infoDialog.dismiss()
            }

            //


        }
        btn_success?.setOnClickListener {
            infoDialog.dismiss()
        }
        btn_cancel?.setOnClickListener {
            infoDialog.dismiss()
        }
        infoDialog.show()
    }

    override fun onCart(cart: Cart) {
       viewModel?.deleteCartList(token!!,SharedPreferenceUtil.getShared(context!!, SharedPreferenceUtil.TYPE_SHOP_ID)!!.toInt(),cart?.ProductId!!)



    }

    override fun onPosition(position: Int) {
        list?.clear()
        list_cart_order?.clear()
        Log.e("cart","cart"+Gson().toJson(cart_list))
        cartAdapter?.notifyDataSetChanged()
        if(cart_list?.size!!<1){
            onData()
        }
        else{
            var price: Double? = 0.0
            for (i in cart_list?.indices!!) {
                price = price!! + (cart_list?.get(i)!!.Price!! * cart_list?.get(i)!!.Quantity!!)
                var dataa = OrderPost(cart_list?.get(i)!!.Name!!, cart_list?.get(i)!!.Quantity!!,cart_list?.get(i)!!.Price!!, cart_list?.get(i)!!.ProductId!!,1,SharedPreferenceUtil.getShared(context!!, SharedPreferenceUtil.TYPE_SHOP_ID)!!.toInt(),cart_list?.get(i)!!.Picture!!,cart_list?.get(i)!!.Created!!)
                var data_cart=CartOrderPost(SharedPreferenceUtil.getShared(context!!, SharedPreferenceUtil.TYPE_SHOP_ID)!!.toInt(),cart_list?.get(i)!!.ProductId!!,2)
                list_cart_order?.add(data_cart)
                list?.add(dataa)
            }
            tv_total?.text="Total: "+price.toString()
        }

    }

    var tokenData:String?=""
    override fun onLoad(data: String) {
        tokenData=data
    }

    override fun onShow(shop: Shop) {
        latitude=shop?.Latitude
        longitude=shop?.Longitude
    }
}
