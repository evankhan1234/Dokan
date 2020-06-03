package com.evan.dokan.ui.home.newsfeed

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.evan.dokan.R
import com.evan.dokan.ui.home.newsfeed.ownpost.OwnPostFragment
import com.evan.dokan.ui.home.newsfeed.publicpost.PublicPostFragment
import com.evan.dokan.ui.home.order.DeliveredFragment
import com.evan.dokan.ui.home.order.PendingFragment
import com.evan.dokan.ui.home.order.ProcessingFragment

class NewsfeedPagerAdapter (fm: FragmentManager, val context: Context) : FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    var firstTab: PublicPostFragment?= PublicPostFragment()
    var secondtTab: OwnPostFragment?= OwnPostFragment()


    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                firstTab!!
            }
            1 ->   secondtTab!!
            else -> {
                return secondtTab!!
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> context.resources.getString(R.string.public_post)
            1 -> context.resources.getString(R.string.own_post)
            else -> {
                return context.resources.getString(R.string.own_post)
            }
        }
    }
}