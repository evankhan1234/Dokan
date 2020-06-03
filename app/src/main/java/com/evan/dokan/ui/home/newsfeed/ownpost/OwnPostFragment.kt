package com.evan.dokan.ui.home.newsfeed.ownpost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evan.dokan.R
import com.evan.dokan.data.db.entities.Order
import com.evan.dokan.data.db.entities.Post
import com.evan.dokan.ui.home.HomeActivity
import com.evan.dokan.ui.home.order.OrderAdapter
import com.evan.dokan.ui.home.order.modelfactory.DeliveredOrderModelFactory
import com.evan.dokan.ui.home.order.viewmodel.DeliveredOrderViewModel
import com.evan.dokan.util.NetworkState
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class OwnPostFragment : Fragment(),KodeinAware,IOwnPostUpdatedListener {

    override val kodein by kodein()

    private val factory : OwnPostModelFactory by instance()

    var ownPostAdapter:OwnPostAdapter?=null


    private lateinit var viewModel: OwnPostViewModel

    var rcv_post: RecyclerView?=null

    var progress_bar: ProgressBar?=null
    var token:String?=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_own_post, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(OwnPostViewModel::class.java)
        rcv_post = root?.findViewById(R.id.rcv_post)
        initAdapter()
        initState()
        return root
    }

    private fun initAdapter() {
        ownPostAdapter = OwnPostAdapter(context!!,this)
        rcv_post?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        rcv_post?.adapter = ownPostAdapter
        startListening()

    }

    private fun startListening() {


        viewModel.listOfAlerts?.observe(this, Observer {
            ownPostAdapter?.submitList(it)
        })

    }


    private fun initState() {
        viewModel.getNetworkState().observe(this, Observer { state ->
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

    }
}