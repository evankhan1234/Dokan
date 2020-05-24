package com.evan.dokan.ui.shop

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evan.dokan.R
import com.evan.dokan.data.db.entities.Shop
import com.evan.dokan.ui.home.HomeActivity

import com.evan.dokan.util.NetworkState
import com.evan.dokan.util.SharedPreferenceUtil
import com.evan.dokan.util.hide
import com.evan.dokan.util.show
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
private const val PERMISSION_REQUEST = 10
class ShopActivity : AppCompatActivity(), KodeinAware,IShopListener,IShopUpdateListener {
    override val kodein by kodein()

    private val factory : ShopModelFactory by instance()

    var shop_adapter:ShopAdapter?=null
    var shoplist_adapter:ShopListAdapter?=null
    var activity: Activity?=null
    private  var viewModel: ShopViewModel?=null

    var rcv_shop:RecyclerView?=null
    var rcv_shop_search:RecyclerView?=null
    var progress_bar: ProgressBar?=null
    var btn_category_new: ImageView?=null
    var edit_content: EditText?=null
    var tv_status: TextView?=null
    var token:String?=""
    var latitude:String?=""
    private var fresh: String? = ""
    lateinit var locationManager: LocationManager
    private var hasGps = false
    private var hasNetwork = false
    private var locationGps: Location? = null
    private var locationNetwork: Location? = null

    private var permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        viewModel = ViewModelProviders.of(this, factory).get(ShopViewModel::class.java)
         viewModel?.shopListener=this
        activity=this
        edit_content=findViewById(R.id.edit_content)
        rcv_shop=findViewById(R.id.rcv_shop)
        tv_status=findViewById(R.id.tv_status)
        progress_bar=findViewById(R.id.progress_bar)
        rcv_shop_search=findViewById(R.id.rcv_shop_search)
        edit_content?.addTextChangedListener(keyword)
        fresh = SharedPreferenceUtil.getShared(this, SharedPreferenceUtil.TYPE_FRESH)
        token = SharedPreferenceUtil.getShared(this, SharedPreferenceUtil.TYPE_AUTH_TOKEN)
        latitude = SharedPreferenceUtil.getShared(this, SharedPreferenceUtil.TYPE_LATITUDE)
        if(fresh != null && !fresh?.trim().equals("") && !fresh.isNullOrEmpty()) {
            tv_status?.visibility=View.GONE
            initAdapter()
            initState()
        }
        else{
            progress_bar?.show()
            tv_status?.isSelected=true
            tv_status?.visibility=View.VISIBLE
            Handler().postDelayed(Runnable {
                progress_bar?.visibility= View.GONE
                tv_status?.visibility=View.GONE
                initAdapter()
                initState()
            },10000)
            SharedPreferenceUtil.saveShared(
                this,
                SharedPreferenceUtil.TYPE_FRESH,
                "Fresh"
            )
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission(permissions)) {
                enableView()
            } else {
                requestPermissions(permissions, PERMISSION_REQUEST)
            }
        } else {
            enableView()
        }
    }
    fun replace(){
        viewModel?.replaceSubscription(this)
        startListening()
    }
    override fun onResume() {
        super.onResume()
        // viewModel.getCategoryType(token!!)
        Log.e("stop","stop")

    }

    private fun initAdapter() {
        shop_adapter = ShopAdapter(this,this)
        rcv_shop?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rcv_shop?.adapter = shop_adapter
        startListening()

    }

    private fun startListening() {
        rcv_shop_search?.visibility= View.GONE
        rcv_shop?.visibility= View.VISIBLE

        viewModel?.listOfAlerts?.observe(this, Observer {
            shop_adapter?.submitList(it)
        })

    }


    private fun initState() {
        viewModel?.getNetworkState()?.observe(this, Observer { state ->
            when (state.status) {
                NetworkState.Status.LOADIND -> {
                    progress_bar?.visibility= View.VISIBLE
                }
                NetworkState.Status.SUCCESS -> {
                    progress_bar?.visibility= View.GONE
                }
                NetworkState.Status.FAILED -> {
                    progress_bar?.visibility= View.GONE
                }
            }
        })
    }

    override fun show(data: MutableList<Shop>) {
        viewModel?.replaceSubscription(this)
        rcv_shop_search?.visibility= View.VISIBLE
        rcv_shop?.visibility= View.GONE
        shoplist_adapter = ShopListAdapter(this,data!!,this)
        rcv_shop_search?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
            adapter = shoplist_adapter
        }
    }

    override fun started() {
        progress_bar?.show()
    }

    override fun failure(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    override fun end() {
        progress_bar?.hide()
    }

    override fun onUpdate(shop: Shop) {
        val intent =Intent(this,HomeActivity::class.java)
        intent.putExtra("ShopUserId",shop?.ShopUserId)
        startActivity(intent)
        SharedPreferenceUtil.saveShared(this, SharedPreferenceUtil.TYPE_SHOP_ID, shop?.ShopUserId.toString())

    }
    var keyword: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun afterTextChanged(s: Editable) {

            try {
                if (s.toString().equals("")){
                    startListening()
                }
                else{
                    var keyword:String?=""
                    keyword=s.toString()+"%"
                    viewModel?.getSearch(token!!,keyword)
                }

            } catch (e: Exception) {

            }


        }

    }
    override fun exit(){
        rcv_shop_search?.visibility= View.GONE
        rcv_shop?.visibility= View.GONE
        viewModel?.replaceSubscription(this)
    }

    private fun enableView() {
        getLocation()
        //Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (hasGps || hasNetwork) {

            if (hasGps) {
                Log.d("CodeAndroidLocation", "hasGps")
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0F, object :
                    LocationListener {
                    override fun onLocationChanged(location: Location?) {
                        if (location != null) {
                            locationGps = location
                            SharedPreferenceUtil.saveShared(activity!!, SharedPreferenceUtil.TYPE_LATITUDE, locationGps!!.latitude.toString())
                            SharedPreferenceUtil.saveShared(activity!!, SharedPreferenceUtil.TYPE_LONGITUDE, locationGps!!.longitude.toString())
                            Log.d("rear", " GPS Latitude : " + locationGps!!.latitude)
                            Log.d("rear", " GPS Longitude : " + locationGps!!.longitude)
                        }
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

                    }

                    override fun onProviderEnabled(provider: String?) {

                    }

                    override fun onProviderDisabled(provider: String?) {

                    }

                })

                val localGpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (localGpsLocation != null)
                    locationGps = localGpsLocation
            }
            if (hasNetwork) {
                Log.d("CodeAndroidLocation", "hasGps")
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0F, object :
                    LocationListener {
                    override fun onLocationChanged(location: Location?) {
                        if (location != null) {
                            locationNetwork = location

                            Log.d("rears", " Network Latitude : " + locationNetwork!!.latitude)
                            Log.d("rears", " Network Longitude : " + locationNetwork!!.longitude)
                        }
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

                    }

                    override fun onProviderEnabled(provider: String?) {

                    }

                    override fun onProviderDisabled(provider: String?) {

                    }

                })

                val localNetworkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if (localNetworkLocation != null)
                    locationNetwork = localNetworkLocation
            }

            if(locationGps!= null && locationNetwork!= null){
                if(locationGps!!.accuracy > locationNetwork!!.accuracy){

                    Log.d("rearsa", " Network Latitude : " + locationNetwork!!.latitude)
                    Log.d("rearsa", " Network Longitude : " + locationNetwork!!.longitude)
                }else{

                    Log.d("CodeAndroidLocation", " GPS Latitude : " + locationGps!!.latitude)
                    Log.d("CodeAndroidLocation", " GPS Longitude : " + locationGps!!.longitude)
                }
            }

        } else {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }

    private fun checkPermission(permissionArray: Array<String>): Boolean {
        var allSuccess = true
        for (i in permissionArray.indices) {
            if (checkCallingOrSelfPermission(permissionArray[i]) == PackageManager.PERMISSION_DENIED)
                allSuccess = false
        }
        return allSuccess
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST) {
            var allSuccess = true
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    allSuccess = false
                    val requestAgain = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(permissions[i])
                    if (requestAgain) {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Go to settings and enable the permission", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            if (allSuccess)
                enableView()

        }
    }
}

