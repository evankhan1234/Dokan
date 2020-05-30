package com.evan.dokan.ui.home.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.TextSliderView
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
    var edit_content: EditText?=null
    var slider: SliderLayout?=null
    var rcv_category: RecyclerView?=null
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
        edit_content=root?.findViewById(R.id.edit_content)
        rcv_category=root?.findViewById(R.id.rcv_category)
        slider=root?.findViewById(R.id.slider)
        edit_content?.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, m: MotionEvent): Boolean {
                if (activity is HomeActivity) {
                    (activity as HomeActivity).goToProductSearchFragment()
                }
                return false
            }
        })
        val textSliderView = TextSliderView(context!!)
        val textSliderView2 = TextSliderView(context!!)
        val textSliderView1 = TextSliderView(context!!)
        textSliderView.description("Store").image("https://i.pinimg.com/736x/6b/69/3b/6b693b3c9b002841406cec19a9e1e1f4.jpg")
        textSliderView2.description("Shop").image("https://www.retaildetail.eu/sites/default/files/styles/news/public/news/shutterstock_324322787_0.jpg?itok=ypsEGJja")
            .setScaleType(BaseSliderView.ScaleType.Fit)
        textSliderView1.description("Mart").image("https://m.economictimes.com/thumb/msid-73320353,width-1200,height-900,resizemode-4,imgsize-789754/kirana-bccl.jpg")
            .setScaleType(BaseSliderView.ScaleType.Fit)
        slider?.addSlider(textSliderView)
        slider?.addSlider(textSliderView1)
        slider?.addSlider(textSliderView2)
        rcv_category?.setNestedScrollingEnabled(false)

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
