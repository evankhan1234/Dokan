package com.evan.dokan.ui.home.dashboard.product

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.evan.dokan.R
import com.evan.dokan.data.db.entities.Product
import com.evan.dokan.ui.home.HomeActivity
import com.evan.dokan.ui.home.dashboard.product.details.ProductDetailsFragment
import com.evan.dokan.util.NetworkState
import com.evan.dokan.util.SharedPreferenceUtil
import com.evan.dokan.util.hide
import com.evan.dokan.util.show
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class ProductCategoryWiseListFragment : Fragment() , KodeinAware,IProductCategoryWiseListener,IProductCategoryWiseUpdateListener {
    override val kodein by kodein()

    private val factory : ProductCategoryWiseModelFactory by instance()

    var productAdapter:ProductCategoryWiseAdapter?=null
    var productSearchAdapter:ProductCategoryWiseSearchAdapter?=null

    private lateinit var viewModel: ProductCategoryWiseViewModel

    var rcv_category: RecyclerView?=null
    var rcv_category_search: RecyclerView?=null
    var progress_bar: ProgressBar?=null
    var edit_content: EditText?=null
    var token:String?=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_product_category_wise_list, container, false)
        rcv_category_search=root?.findViewById(R.id.rcv_category_search)
        progress_bar=root?.findViewById(R.id.progress_bar)
        rcv_category=root?.findViewById(R.id.rcv_category)
        edit_content=root?.findViewById(R.id.edit_content)
        viewModel = ViewModelProviders.of(this, factory).get(ProductCategoryWiseViewModel::class.java)

        viewModel.productCategoryWiseListener=this
        token = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
        edit_content?.addTextChangedListener(keyword)
        return root
    }
    fun replace(){
        viewModel.replaceSubscription(this)
        startListening()
    }
    override fun onResume() {
        super.onResume()
        // viewModel.getCategoryType(token!!)
        Log.e("stop","stop")
        initAdapter()
        initState()
    }

    private fun initAdapter() {
        productAdapter = ProductCategoryWiseAdapter(context!!,this)
        rcv_category?.layoutManager = GridLayoutManager(context, 2)
        rcv_category?.adapter = productAdapter
        startListening()

    }

    private fun startListening() {
        rcv_category_search?.visibility=View.GONE
        rcv_category?.visibility=View.VISIBLE

        viewModel.listOfAlerts?.observe(this, Observer {
            productAdapter?.submitList(it)
        })

    }


    private fun initState() {
        viewModel.getNetworkState().observe(this, Observer { state ->
            when (state.status) {
                NetworkState.Status.LOADIND -> {
                    progress_bar?.visibility=View.VISIBLE
                }
                NetworkState.Status.SUCCESS -> {
                    progress_bar?.visibility=View.GONE
                }
                NetworkState.Status.FAILED -> {
                    progress_bar?.visibility=View.GONE
                }
            }
        })
    }

    fun removeChild() {
        val f =
            childFragmentManager.findFragmentByTag(ProductDetailsFragment::class.java.simpleName)
        val f1 = childFragmentManager.findFragmentByTag(ProductDetailsFragment::class.java.simpleName)
        if (f != null) {
            val transaction = childFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right)
            transaction.remove(f)
            transaction.commit()
            childFragmentManager.popBackStack()
        }
    }

    override fun show(data: MutableList<Product>) {
        viewModel.replaceSubscription(this)
        rcv_category_search?.visibility=View.VISIBLE
        rcv_category?.visibility=View.GONE
        productSearchAdapter = ProductCategoryWiseSearchAdapter(context!!,data!!,this)
        rcv_category_search?.apply {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = productSearchAdapter
        }
    }

    override fun started() {
        progress_bar?.show()
    }

    override fun failure(message: String) {
       Toast.makeText(context!!,message,Toast.LENGTH_SHORT).show()
    }

    override fun end() {
        progress_bar?.hide()
    }

    override fun onUpdate(product: Product) {
        if (activity is HomeActivity) {
            (activity as HomeActivity).goToProductDetailsFragment(product)
        }
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
                    viewModel.getSearch(token!!,keyword,SharedPreferenceUtil.getShared(context!!, SharedPreferenceUtil.TYPE_SHOP_ID)!!,
                        SharedPreferenceUtil.getShared(context!!, SharedPreferenceUtil.TYPE_PRODUCT_CATEGORY)!!)
                }

            } catch (e: Exception) {

            }


        }

    }
    override fun exit(){
        rcv_category_search?.visibility=View.GONE
        rcv_category?.visibility=View.GONE
        viewModel.replaceSubscription(this)
    }

}
