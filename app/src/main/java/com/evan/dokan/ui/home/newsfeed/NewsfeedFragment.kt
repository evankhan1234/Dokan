package com.evan.dokan.ui.home.newsfeed

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.evan.dokan.R
import com.evan.dokan.ui.home.order.OrderPagerAdapter
import com.google.android.material.tabs.TabLayout

class NewsfeedFragment : Fragment() {

    private var mContext: Context? = null
    private var viewPager: ViewPager? = null
    private var tabs: TabLayout? = null
    private var indicator: View? = null
    val SELECTED_PAGE:String="page"
    var root:View?=null
    var selectedPage:Int?=-1
    var fragmentAdapter: NewsfeedPagerAdapter?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_newsfeed, container, false)
        mContext = activity
        viewPager = root?.findViewById(R.id.viewPager)
        tabs = root?.findViewById(R.id.tabs)
        indicator = root?.findViewById(R.id.indicator)
        setTabView()
        return root
    }

    fun setTabView() {
        fragmentAdapter= NewsfeedPagerAdapter(childFragmentManager, mContext!!)
        viewPager?.apply {
            this.adapter = fragmentAdapter
            this.offscreenPageLimit=1
            tabs?.setupWithViewPager(viewPager)

        }

        viewPager?.addOnPageChangeListener(object :
            ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, positionOffset: Float, positionOffsetPx: Int) {
            }
            override fun onPageSelected(i: Int) {
                refreshData(i)
            }
            override fun onPageScrollStateChanged(i: Int) {}
        })
    }
    fun refreshData(index:Int?)
    {
        viewPager?.setCurrentItem(index!!)
        if(index==0)
        {
            //  fragmentAdapter?.firstTab?.getDataReload()
        }
        else  if(index==1)
        {
            //  fragmentAdapter?.secondtTab?.getDataFromAPI()
        }else  if(index==2)
        {
            //  fragmentAdapter?.thirdTab?.getDataFromAPI()
        }
        else  if(index==3)
        {
            //  fragmentAdapter?.thirdTab?.getDataFromAPI()
        }
    }
//    fun removeChild() {
//        val f =
//            childFragmentManager.findFragmentByTag(CreateDeliveryFragment::class.java.simpleName)
//        val f1=   fragmentManager?.findFragmentByTag(CreateDeliveryFragment::class.java.simpleName)
//        Log.e("data_for","data"+f1)
//        if (f != null) {
//            val transaction = childFragmentManager.beginTransaction()
//            transaction.setCustomAnimations(R.anim.left_to_right, R.anim.left_to_right)
//            transaction.remove(f)
//            transaction.commit()
//            childFragmentManager.popBackStack()
//        }
//    }

    override fun onResume() {
        super.onResume()
        Log.e("data","data")
    }
    fun reload(){
        fragmentAdapter?.secondtTab?.reloadData()
        fragmentAdapter?.firstTab?.reloadData()
    }
}
