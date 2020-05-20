package com.evan.dokan.ui.home.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

import com.evan.dokan.R
import com.evan.dokan.data.db.entities.Category
import com.evan.dokan.ui.home.HomeActivity
import com.evan.dokan.ui.home.HomeViewModel
import com.evan.dokan.ui.home.HomeViewModelFactory
import com.evan.dokan.ui.home.dashboard.category.CategoryAdapter
import com.evan.dokan.ui.home.dashboard.category.ICategoryListListener
import com.evan.dokan.ui.home.dashboard.category.ICategoryUpdateListener
import com.evan.dokan.ui.home.dashboard.product.ProductCategoryWiseListFragment
import com.evan.dokan.util.SharedPreferenceUtil
import com.evan.dokan.util.hide
import com.evan.dokan.util.show
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class DashboardFragment : Fragment(),KodeinAware, ICategoryListListener , ICategoryUpdateListener {
    override val kodein by kodein()

    var categoryAdapter: CategoryAdapter?=null
    private val factory : HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    var token:String?=""
    var shopUserId:Int?=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_dashboard, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        viewModel.categoryListListener=this
        token = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
        val args: Bundle? = arguments
        shopUserId = args?.getInt("ShopUserId")
        viewModel.getCategory(token!!,shopUserId!!)
        return root
    }

    override fun category(data: MutableList<Category>?) {
        categoryAdapter = CategoryAdapter(context!!,data!!,this)
        rcv_category?.apply {
            layoutManager = GridLayoutManager(context, 3)
            setHasFixedSize(true)
            adapter = categoryAdapter
        }
    }

    override fun onStarted() {
        progress_bar?.show()
    }

    override fun onEnd() {
        progress_bar?.hide()
    }

    override fun onUpdate(category: Category) {
        SharedPreferenceUtil.saveShared(activity!!, SharedPreferenceUtil.TYPE_PRODUCT_CATEGORY, category?.Id.toString())
        if (activity is HomeActivity) {
          (activity as HomeActivity).goToProductListCategoryFragment()
        }
    }
    fun removeChild() {
        val f =
            childFragmentManager.findFragmentByTag(ProductCategoryWiseListFragment::class.java.simpleName)
        val f1 = childFragmentManager.findFragmentByTag(ProductCategoryWiseListFragment::class.java.simpleName)
        if (f != null) {
            val transaction = childFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right)
            transaction.remove(f)
            transaction.commit()
            childFragmentManager.popBackStack()
        }
    }
}
