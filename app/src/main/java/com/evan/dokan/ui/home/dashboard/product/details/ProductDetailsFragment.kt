package com.evan.dokan.ui.home.dashboard.product.details

import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide

import com.evan.dokan.R
import com.evan.dokan.data.db.entities.Product
import com.evan.dokan.data.network.post.Push
import com.evan.dokan.data.network.post.PushPost
import com.evan.dokan.ui.home.HomeViewModel
import com.evan.dokan.ui.home.HomeViewModelFactory
import com.evan.dokan.ui.home.dashboard.category.CategoryAdapter
import com.evan.dokan.util.SharedPreferenceUtil
import com.google.gson.Gson
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class ProductDetailsFragment : Fragment() ,KodeinAware,IProductLikeListener{
    override val kodein by kodein()

    var categoryAdapter: CategoryAdapter?=null
    private val factory : HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    var product:Product?=null
    var img_product:ImageView?=null
    var tv_product_name:TextView?=null
    var tv_product_price:TextView?=null
    var tv_product_code:TextView?=null
    var tv_product_details:TextView?=null
    var tv_product_stock:TextView?=null
    var text_quantity:TextView?=null
    var img_like:ImageView?=null
    var img_plus:ImageView?=null
    var img_minus:ImageView?=null
    var is_like:Boolean?=null
    var token:String?=""
    var quantity:Int?=1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_product_details, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        viewModel.productLikeListener=this
        img_product=root?.findViewById(R.id.img_product)
        img_plus=root?.findViewById(R.id.img_plus)
        img_like=root?.findViewById(R.id.img_like)
        text_quantity=root?.findViewById(R.id.text_quantity)
        tv_product_name=root?.findViewById(R.id.tv_product_name)
        tv_product_code=root?.findViewById(R.id.tv_product_code)
        tv_product_price=root?.findViewById(R.id.tv_product_price)
        tv_product_details=root?.findViewById(R.id.tv_product_details)
        tv_product_stock=root?.findViewById(R.id.tv_product_stock)
        img_minus=root?.findViewById(R.id.img_minus)
        val args: Bundle? = arguments
        if (args != null) {
            if (args?.containsKey(Product::class.java.getSimpleName()) != null) {
                product = args?.getParcelable(Product::class.java.getSimpleName())
                Glide.with(context!!)
                    .load(product?.ProductImage)
                    .into(img_product!!)
                Log.e("data","data"+ Gson().toJson(product))
                var product_name: String = ""
                var product_price: String = ""
                var product_code: String = ""
                var product_details: String = ""
                var product_stock: String = ""
                var total_price: Double? = 0.0
                total_price=product?.SellPrice!!-product?.Discount!!
                product_name = "<b> <font color=#BF3E15>Product Name: </font> : </b>" + product?.Name.toString()
                product_stock = "<b> <font color=#BF3E15>Stock: </font> : </b>" + product?.Stock.toString()+" "+product?.UnitName
                product_code = "<b> <font color=#BF3E15>Product Code: </font> : </b>" + product?.ProductCode.toString()
                product_details = "<b> <font color=#BF3E15>Product Details: </font> : </b>" + product?.Details.toString()
                product_price = "<b> <font color=#BF3E15>Product Price : ট </font>  </b>" + total_price.toString()
                tv_product_name?.text = Html.fromHtml(product_name!!)
                tv_product_code?.text = Html.fromHtml(product_code!!)
                tv_product_details?.text = Html.fromHtml(product_details!!)
                tv_product_price?.text = Html.fromHtml(product_price!!)
                tv_product_stock?.text = Html.fromHtml(product_stock!!)


            }

        }
        token = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
        viewModel.getProductLike(token!!,product?.Id!!,product?.ShopUserId!!)
        img_like?.setOnClickListener {
            if (!is_like!!) {

                img_like?.isSelected = true
                is_like = true
            } else {

                img_like?.isSelected = false
                is_like = false
            }
        }

        img_plus?.setOnClickListener {
            quantity=quantity!!+1
            text_quantity?.text=quantity!!.toString()
        }
        img_minus?.setOnClickListener {
            quantity=quantity!!-1
            if(quantity!!<1){
                quantity=1
            }
            else{
                text_quantity?.text=quantity!!.toString()
            }

        }
            return root
    }

    override fun onBoolean(response: Boolean) {
        is_like=response
        img_like?.isSelected = response!!
    }

}
