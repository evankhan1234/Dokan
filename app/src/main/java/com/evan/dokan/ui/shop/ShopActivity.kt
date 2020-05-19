package com.evan.dokan.ui.shop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evan.dokan.R
import com.evan.dokan.data.db.entities.Shop
import com.evan.dokan.util.NetworkState
import com.evan.dokan.util.SharedPreferenceUtil
import com.evan.dokan.util.hide
import com.evan.dokan.util.show
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ShopActivity : AppCompatActivity(), KodeinAware,IShopListener,IShopUpdateListener {
    override val kodein by kodein()

    private val factory : ShopModelFactory by instance()

    var shop_adapter:ShopAdapter?=null
    var shoplist_adapter:ShopListAdapter?=null

    private lateinit var viewModel: ShopViewModel

    var rcv_shop:RecyclerView?=null
    var rcv_shop_search:RecyclerView?=null
    var progress_bar: ProgressBar?=null
    var btn_category_new: ImageView?=null
    var edit_content: EditText?=null
    var tv_status: TextView?=null
    var token:String?=""
    private var fresh: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        viewModel = ViewModelProviders.of(this, factory).get(ShopViewModel::class.java)
         viewModel.shopListener=this
        edit_content=findViewById(R.id.edit_content)
        rcv_shop=findViewById(R.id.rcv_shop)
        tv_status=findViewById(R.id.tv_status)
        progress_bar=findViewById(R.id.progress_bar)
        rcv_shop_search=findViewById(R.id.rcv_shop_search)
        edit_content?.addTextChangedListener(keyword)
        fresh = SharedPreferenceUtil.getShared(this, SharedPreferenceUtil.TYPE_FRESH)
        token = SharedPreferenceUtil.getShared(this, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
        if(fresh != null && !fresh?.trim().equals("") && !fresh.isNullOrEmpty()) {
            tv_status?.visibility=View.GONE
            initAdapter()
            initState()
        }
        else{
            progress_bar?.show()
            tv_status?.isSelected=true
            tv_status?.visibility=View.VISIBLE
            Handler().postDelayed(Runnable {
                progress_bar?.visibility= View.GONE
                tv_status?.visibility=View.GONE
                initAdapter()
                initState()
            },10000)
            SharedPreferenceUtil.saveShared(
                this,
                SharedPreferenceUtil.TYPE_FRESH,
                "Fresh"
            )
        }

    }
    fun replace(){
        viewModel.replaceSubscription(this)
        startListening()
    }
    override fun onResume() {
        super.onResume()
        // viewModel.getCategoryType(token!!)
        Log.e("stop","stop")

    }

    private fun initAdapter() {
        shop_adapter = ShopAdapter(this,this)
        rcv_shop?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rcv_shop?.adapter = shop_adapter
        startListening()

    }

    private fun startListening() {
        rcv_shop_search?.visibility= View.GONE
        rcv_shop?.visibility= View.VISIBLE

        viewModel.listOfAlerts?.observe(this, Observer {
            shop_adapter?.submitList(it)
        })

    }


    private fun initState() {
        viewModel.getNetworkState().observe(this, Observer { state ->
            when (state.status) {
                NetworkState.Status.LOADIND -> {
                    progress_bar?.visibility= View.VISIBLE
                }
                NetworkState.Status.SUCCESS -> {
                    progress_bar?.visibility= View.GONE
                }
                NetworkState.Status.FAILED -> {
                    progress_bar?.visibility= View.GONE
                }
            }
        })
    }

    override fun show(data: MutableList<Shop>) {
        viewModel.replaceSubscription(this)
        rcv_shop_search?.visibility= View.VISIBLE
        rcv_shop?.visibility= View.GONE
        shoplist_adapter = ShopListAdapter(this,data!!,this)
        rcv_shop_search?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
            adapter = shoplist_adapter
        }
    }

    override fun started() {
        progress_bar?.show()
    }

    override fun failure(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    override fun end() {
        progress_bar?.hide()
    }

    override fun onUpdate(shop: Shop) {

    }
    var keyword: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun afterTextChanged(s: Editable) {

            try {
                if (s.toString().equals("")){
                    startListening()
                }
                else{
                    var keyword:String?=""
                    keyword=s.toString()+"%"
                    viewModel.getSearch(token!!,keyword)
                }

            } catch (e: Exception) {

            }


        }

    }
    override fun exit(){
        rcv_shop_search?.visibility= View.GONE
        rcv_shop?.visibility= View.GONE
        viewModel.replaceSubscription(this)
    }
}

