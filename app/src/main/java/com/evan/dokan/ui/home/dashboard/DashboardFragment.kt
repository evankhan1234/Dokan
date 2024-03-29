package com.evan.dokan.ui.home.dashboard

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.TextSliderView
import com.evan.dokan.R
import com.evan.dokan.data.db.entities.Category
import com.evan.dokan.data.db.entities.Product
import com.evan.dokan.ui.home.HomeActivity
import com.evan.dokan.ui.home.HomeViewModel
import com.evan.dokan.ui.home.HomeViewModelFactory
import com.evan.dokan.ui.home.dashboard.category.CategoryAdapter
import com.evan.dokan.ui.home.dashboard.category.ICategoryListListener
import com.evan.dokan.ui.home.dashboard.category.ICategoryUpdateListener
import com.evan.dokan.ui.home.dashboard.product.IProductCategoryWiseUpdateListener
import com.evan.dokan.ui.home.dashboard.product.ProductCategoryWiseListFragment
import com.evan.dokan.ui.home.dashboard.product.ProductCategoryWiseSearchAdapter
import com.evan.dokan.util.SharedPreferenceUtil
import com.evan.dokan.util.hide
import com.evan.dokan.util.show
import com.evan.dokan.util.value_for
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class DashboardFragment : Fragment(),KodeinAware, ICategoryListListener , ICategoryUpdateListener,IRecentProductListener,
    IProductCategoryWiseUpdateListener {
    override val kodein by kodein()

    var categoryAdapter: CategoryAdapter?=null
    private val factory : HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    var token:String?=""
    var pushToken:String?=""
    var shopUserId:Int?=0
    var shopUserName:String?=""
    var edit_content: EditText?=null
    var slider: SliderLayout?=null
    var rcv_category: RecyclerView?=null
    var rcv_products: RecyclerView?=null
    var progress_bar: ProgressBar?=null
    var tv_store: TextView?=null
    var tv_news_feed: TextView?=null
    var productSearchAdapter: ProductCategoryWiseSearchAdapter?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_dashboard, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        viewModel.categoryListListener=this
        viewModel.recentProductListener=this
        token = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
        var fuser = FirebaseAuth.getInstance().currentUser
        val data = fuser!!.uid
         viewModel.createFirebaseId(token!!,2,data)
        pushToken = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_PUSH_TOKEN)
        val args: Bundle? = arguments
        shopUserId = args?.getInt("ShopUserId")
        shopUserName = args?.getString("ShopUserName")
        SharedPreferenceUtil.saveShared(
            activity!!,
            SharedPreferenceUtil.TYPE_SHOP_NAME,
            shopUserName!!
        )
        viewModel.getCategory(token!!,shopUserId!!)
        viewModel.getRecentProduct(token!!,shopUserId!!)
        viewModel.createToken(token!!,2,pushToken!!)
        edit_content=root?.findViewById(R.id.edit_content)
        progress_bar=root?.findViewById(R.id.progress_bar)
        tv_store=root?.findViewById(R.id.tv_store)
        tv_store?.text="Welcome to the "+shopUserName+" Online Shopping Store.Take a tour and shop as you please :)"
        tv_store?.isSelected=true
        rcv_products=root?.findViewById(R.id.rcv_products)
        rcv_category=root?.findViewById(R.id.rcv_category)
        tv_news_feed=root?.findViewById(R.id.tv_news_feed)
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
        rcv_products?.setNestedScrollingEnabled(false)
        tv_news_feed?.setOnClickListener {
            if (activity is HomeActivity) {
                (activity as HomeActivity).goToNewsfeedFragment()
            }
        }
        return root
    }

    override fun category(data: MutableList<Category>?) {
        categoryAdapter = CategoryAdapter(context!!,data!!,this)
        rcv_category?.apply {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = categoryAdapter
        }
    }

    override fun product(data: MutableList<Product>?) {
        productSearchAdapter = ProductCategoryWiseSearchAdapter(context!!, data!!, this)
        rcv_products?.apply {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = productSearchAdapter
        }
    }

    override fun onStarted() {
        progress_bar?.visibility=View.VISIBLE
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
    override fun onUpdate(product: Product) {
        if (activity is HomeActivity) {
            (activity as HomeActivity).goToProductDetailsFragment(product)
        }
    }
}
