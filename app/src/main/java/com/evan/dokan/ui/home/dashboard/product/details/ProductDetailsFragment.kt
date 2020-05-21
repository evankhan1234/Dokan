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
import com.bumptech.glide.Glide

import com.evan.dokan.R
import com.evan.dokan.data.db.entities.Product
import com.google.gson.Gson




class ProductDetailsFragment : Fragment() {


    var product:Product?=null
    var img_product:ImageView?=null
    var tv_product_name:TextView?=null
    var tv_product_price:TextView?=null
    var tv_product_code:TextView?=null
    var tv_product_details:TextView?=null
    var tv_product_stock:TextView?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_product_details, container, false)
        img_product=root?.findViewById(R.id.img_product)
        tv_product_name=root?.findViewById(R.id.tv_product_name)
        tv_product_code=root?.findViewById(R.id.tv_product_code)
        tv_product_price=root?.findViewById(R.id.tv_product_price)
        tv_product_details=root?.findViewById(R.id.tv_product_details)
        tv_product_stock=root?.findViewById(R.id.tv_product_stock)
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
        return root
    }

}
