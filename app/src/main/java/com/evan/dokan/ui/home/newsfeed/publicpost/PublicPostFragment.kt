package com.evan.dokan.ui.home.newsfeed.publicpost

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evan.dokan.R
import com.evan.dokan.data.db.entities.Order
import com.evan.dokan.data.db.entities.Post
import com.evan.dokan.ui.home.HomeActivity
import com.evan.dokan.ui.home.newsfeed.ownpost.PostBottomsheetFragment
import com.evan.dokan.ui.home.order.OrderAdapter
import com.evan.dokan.ui.home.order.modelfactory.DeliveredOrderModelFactory
import com.evan.dokan.ui.home.order.viewmodel.DeliveredOrderViewModel
import com.evan.dokan.util.NetworkState
import com.evan.dokan.util.SharedPreferenceUtil
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class PublicPostFragment : Fragment() ,KodeinAware,IPublicPostUpdateListener,IPublicPostLikeListener{
    override val kodein by kodein()

    private val factory : PublicPostModelFactory by instance()

    var publicPostAdapter:PublicPostAdapter?=null


    var viewModel: PublicPostViewModel?=null

    var rcv_post: RecyclerView?=null

    var progress_bar: ProgressBar?=null
    var swipe_refresh: SwipyRefreshLayout?=null
    var token:String?=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_public_post, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(PublicPostViewModel::class.java)
        rcv_post = root?.findViewById(R.id.rcv_post)
        swipe_refresh = root?.findViewById(R.id.swipe_refresh)
        progress_bar = root?.findViewById(R.id.progress_bar)
        token = SharedPreferenceUtil.getShared(activity!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
        initAdapter()
        initState()
        swipe_refresh?.setOnRefreshListener {
            viewModel?.replaceSubscription(this)
            startListening()
            Handler().postDelayed(Runnable {
                swipe_refresh?.isRefreshing=false
            },1000)

        }
        return root
    }
    private fun initAdapter() {
        publicPostAdapter = PublicPostAdapter(context!!,this,this)
        rcv_post?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        rcv_post?.adapter = publicPostAdapter
        startListening()

    }

    private fun startListening() {


        viewModel?.listOfAlerts?.observe(this, Observer {
            publicPostAdapter?.submitList(it)
        })

    }


    private fun initState() {
        viewModel?.getNetworkState()!!.observe(this, Observer { state ->
            when (state.status) {
                NetworkState.Status.LOADIND -> {
                    progress_bar?.visibility=View.VISIBLE
                }
                NetworkState.Status.SUCCESS -> {
                    progress_bar?.visibility=View.GONE
                }
                NetworkState.Status.FAILED -> {
                    progress_bar?.visibility=View.GONE
                }
            }
        })
    }

    override fun onUpdate(post: Post) {
        val bottomSheetFragment = CommentsFragment()
        val manager =
            (activity!! as AppCompatActivity).supportFragmentManager
        bottomSheetFragment.show(manager, bottomSheetFragment.tag)
    }

    override fun onCount(count: Int?,type:Int,id:Int) {
        publicPostAdapter?.notifyDataSetChanged()
        viewModel?.updatedLikeCount(token!!,id,count!!)
        if(type==1){
            viewModel?.createdLove(token!!,id,2)
        }
        else{
            viewModel?.deletedLove(token!!,id,2)
        }
        Log.e("count","count"+count)
    }
    fun reloadData(){
        viewModel?.replaceSubscription(this)
        startListening()
        Log.e("data","data")
    }
}