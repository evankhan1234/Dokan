package com.evan.dokan.ui.home.wishlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.evan.dokan.R
import com.evan.dokan.data.db.entities.Product
import com.evan.dokan.ui.home.HomeActivity
import com.evan.dokan.ui.home.HomeViewModel
import com.evan.dokan.ui.home.HomeViewModelFactory
import com.evan.dokan.ui.home.dashboard.product.IProductCategoryWiseUpdateListener
import com.evan.dokan.ui.home.dashboard.product.details.ProductDetailsFragment
import com.evan.dokan.util.SharedPreferenceUtil
import com.evan.dokan.util.hide
import com.evan.dokan.util.show
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class WishListFragment : Fragment() ,KodeinAware,IWishListListener,
    IProductCategoryWiseUpdateListener,IWishDeleteListener,IWishListDeleteListener {
    override val kodein by kodein()
    var progress_bar: ProgressBar?=null
    var rcv_wish: RecyclerView?=null
    private val factory : HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    var wishAdapter:WishListAdapter?=null
    var token:String?=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_wish_list, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        viewModel.wishListListener=this
        viewModel.wishDeleteListener=this
        progress_bar=root?.findViewById(R.id.progress_bar)
        rcv_wish=root?.findViewById(R.id.rcv_wish)
        token = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
        viewModel.getWishList(token!!,SharedPreferenceUtil.getShared(context!!, SharedPreferenceUtil.TYPE_SHOP_ID)!!.toInt())
        return root
    }

    override fun wish(data: MutableList<Product>?) {
        wishAdapter = WishListAdapter(context!!,data!!,this,this)
        rcv_wish?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
            adapter = wishAdapter
        }
    }

    override fun onSuccess(message: String) {
        Toast.makeText(context!!,message, Toast.LENGTH_SHORT).show()
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

    override fun onUpdate(product: Product) {
        if (activity is HomeActivity) {
            (activity as HomeActivity).goToProductDetailsFragment(product)
        }
    }

    override fun onProduct(product: Product) {
        viewModel?.deleteWishList(token!!,product?.ShopUserId!!,product?.Id!!)
        wishAdapter?.notifyDataSetChanged()
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

}
