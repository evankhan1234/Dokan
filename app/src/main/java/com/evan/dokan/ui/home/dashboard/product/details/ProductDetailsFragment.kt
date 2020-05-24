package com.evan.dokan.ui.home.dashboard.product.details

import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide

import com.evan.dokan.R
import com.evan.dokan.data.db.entities.Product
import com.evan.dokan.ui.home.HomeActivity
import com.evan.dokan.ui.home.HomeViewModel
import com.evan.dokan.ui.home.HomeViewModelFactory
import com.evan.dokan.ui.home.dashboard.category.CategoryAdapter
import com.evan.dokan.ui.home.wishlist.IWishDeleteListener
import com.evan.dokan.ui.home.wishlist.IWishListCreateListener
import com.evan.dokan.util.SharedPreferenceUtil
import com.evan.dokan.util.hide
import com.evan.dokan.util.show
import com.google.gson.Gson
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*


class ProductDetailsFragment : Fragment() ,KodeinAware,IProductLikeListener,
    IWishListCreateListener, IWishDeleteListener,ICreateCartListener {
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
    var btn_ok:Button?=null
    var progress_bar:ProgressBar?=null
    var is_like:Boolean?=null
    var token:String?=""
    var quantity:Int?=1
    var total_price: Double? = 0.0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_product_details, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        viewModel.productLikeListener=this
        viewModel.wishListCreateListener=this
        viewModel.wishDeleteListener=this
        viewModel.createCartListener=this
        btn_ok=root?.findViewById(R.id.btn_ok)
        img_product=root?.findViewById(R.id.img_product)
        progress_bar=root?.findViewById(R.id.progress_bar)
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
        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        val currentDate = sdf.format(Date())
        token = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
        viewModel.getProductLike(token!!,product?.Id!!,product?.ShopUserId!!)
        img_like?.setOnClickListener {
            if (!is_like!!) {
                viewModel?.createWishList(token!!,product?.ShopUserId!!,product?.Id!!,1,currentDate)
                img_like?.isSelected = true
                is_like = true
            } else {
                viewModel?.deleteWishList(token!!,product?.ShopUserId!!,product?.Id!!)
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
        btn_ok?.setOnClickListener {

            val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            val currentDate = sdf.format(Date())
            viewModel.createCart(token!!,product?.Name!!,total_price!!,quantity!!,product?.Id!!,1,product?.ShopUserId!!,product?.ProductImage!!,currentDate)
        }
            return root
    }

    override fun onBoolean(response: Boolean) {
        is_like=response
        img_like?.isSelected = response!!
    }

    override fun onSuccess(message: String) {
       Toast.makeText(context!!,message,Toast.LENGTH_SHORT).show()
        if (activity is HomeActivity) {
            (activity as HomeActivity).onCount()
        }
    }

    override fun onSuccessCart(message: String) {
        Toast.makeText(context!!,message,Toast.LENGTH_SHORT).show()
        if (activity is HomeActivity) {
            (activity as HomeActivity).onBackPressed()
        }
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
        Toast.makeText(context!!,message,Toast.LENGTH_SHORT).show()
    }

}
