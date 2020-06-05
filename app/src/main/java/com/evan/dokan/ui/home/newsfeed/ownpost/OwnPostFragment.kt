package com.evan.dokan.ui.home.newsfeed.ownpost

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evan.dokan.R
import com.evan.dokan.data.db.entities.Post
import com.evan.dokan.util.NetworkState
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class OwnPostFragment : Fragment(),KodeinAware,IOwnPostUpdatedListener {

    override val kodein by kodein()

    private val factory : OwnPostModelFactory by instance()

    var ownPostAdapter:OwnPostAdapter?=null


    private  var viewModel: OwnPostViewModel?=null

    var rcv_post: RecyclerView?=null
    var swipe_refresh: SwipyRefreshLayout?=null
    var progress_bar: ProgressBar?=null
    var img_post_new: ImageView?=null
    var token:String?=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_own_post, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(OwnPostViewModel::class.java)
        rcv_post = root?.findViewById(R.id.rcv_post)
        img_post_new = root?.findViewById(R.id.img_post_new)
        swipe_refresh = root?.findViewById(R.id.swipe_refresh)
        progress_bar = root?.findViewById(R.id.progress_bar)
        initAdapter()
        initState()
        img_post_new?.setOnClickListener {
            val bottomSheetFragment = PostBottomsheetFragment(null)
            val manager =
                (activity!! as AppCompatActivity).supportFragmentManager
            bottomSheetFragment.show(manager, bottomSheetFragment.tag)
        }
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
        ownPostAdapter = OwnPostAdapter(context!!,this)
        rcv_post?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        rcv_post?.adapter = ownPostAdapter
        startListening()

    }

    private fun startListening() {


        viewModel?.listOfAlerts?.observe(this, Observer {
            ownPostAdapter?.submitList(it)
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
        val bottomSheetFragment = PostBottomsheetFragment(post)
        val manager =
            (activity!! as AppCompatActivity).supportFragmentManager
        bottomSheetFragment.show(manager, bottomSheetFragment.tag)
    }
    override fun onResume() {
        super.onResume()
        Log.e("data","data")
    }
    fun reloadData(){
        viewModel?.replaceSubscription(this)
        startListening()
        Log.e("data","data")
    }
}