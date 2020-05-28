package com.evan.dokan.ui.home.order

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.evan.dokan.R

class OrderPagerAdapter (fm: FragmentManager, val context: Context) : FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    var firstTab:PendingFragment?= PendingFragment()
    var secondtTab: ProcessingFragment?=ProcessingFragment()
    var thirdtTab: DeliveredFragment?=DeliveredFragment()


    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                firstTab!!
            }
            1 ->   secondtTab!!
            2 ->   thirdtTab!!
            else -> {
                return secondtTab!!
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> context.resources.getString(R.string.pending)
            1 -> context.resources.getString(R.string.processing)
            2 -> context.resources.getString(R.string.delivered)
            else -> {
                return context.resources.getString(R.string.delivered)
            }
        }
    }
}