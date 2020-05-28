package com.evan.dokan.ui.auth

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
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.evan.dokan.ui.home.HomeActivity
import kotlinx.android.synthetic.main.activity_login.*
import com.evan.dokan.R
import com.evan.dokan.data.db.entities.Users
import com.evan.dokan.databinding.ActivityLoginBinding
import com.evan.dokan.ui.auth.interfaces.AuthListener
import com.evan.dokan.ui.shop.ShopActivity
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

import android.provider.Settings
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.evan.dokan.util.*

private const val PERMISSION_REQUEST = 10
class LoginActivity : AppCompatActivity(),
    AuthListener, KodeinAware {

    var activity:Activity?=null
    override val kodein by kodein()
    private val factory : AuthViewModelFactory by instance()
    lateinit var locationManager: LocationManager
    private var hasGps = false
    private var hasNetwork = false
    private var locationGps: Location? = null
    private var locationNetwork: Location? = null

    private var permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.authListener = this
        activity=this
        et_password?.transformationMethod = MyPasswordTransformationMethod()

        show_pass?.setOnClickListener {
            onPasswordVisibleOrInvisible()
        }
        btn_create_account?.setOnClickListener {
            Intent(this, CreateAccountActivity::class.java).also {

                startActivity(it)
            }
        }

//        et_email?.setOnTouchListener(object : View.OnTouchListener {
//            override fun onTouch(v: View, m: MotionEvent): Boolean {
//                // Perform tasks here
//                enableView()
//
//                return false
//            }
//        })
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

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess(user: Users) {
        progress_bar.hide()
        Intent(this, ShopActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }

    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }
    fun onPasswordVisibleOrInvisible() {
        val cursorPosition = et_password?.selectionStart

        if (et_password?.transformationMethod == null) {
            et_password?.transformationMethod = MyPasswordTransformationMethod()
            show_pass?.isSelected = false
        } else {

            et_password?.transformationMethod = null
            show_pass?.isSelected = true
        }
        et_password?.setSelection(cursorPosition!!)
    }


    private fun enableView() {
        getLocation()
       // Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
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
                            Log.d("Evan", " GPS Latitude : " + locationGps!!.latitude)
                            Log.d("Evan", " GPS Longitude : " + locationGps!!.longitude)
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
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0F, object : LocationListener {
                    override fun onLocationChanged(location: Location?) {
                        if (location != null) {
                            locationNetwork = location

                            Log.d("Khan", " Network Latitude : " + locationNetwork!!.latitude)
                            Log.d("Khan", " Network Longitude : " + locationNetwork!!.longitude)
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
                    SharedPreferenceUtil.saveShared(activity!!, SharedPreferenceUtil.TYPE_LATITUDE, locationGps!!.latitude.toString())
                    SharedPreferenceUtil.saveShared(activity!!, SharedPreferenceUtil.TYPE_LONGITUDE, locationGps!!.longitude.toString())
                    Log.d("CodeAndroidLocation", " Network Latitude : " + locationNetwork!!.latitude)
                    Log.d("CodeAndroidLocation", " Network Longitude : " + locationNetwork!!.longitude)
                }else{
                    SharedPreferenceUtil.saveShared(activity!!, SharedPreferenceUtil.TYPE_LATITUDE, locationGps!!.latitude.toString())
                    SharedPreferenceUtil.saveShared(activity!!, SharedPreferenceUtil.TYPE_LONGITUDE, locationGps!!.longitude.toString())
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


