package com.evan.dokan.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.evan.dokan.BuildConfig
import com.evan.dokan.R
import com.evan.dokan.data.db.entities.Order
import com.evan.dokan.data.db.entities.Product
import com.evan.dokan.data.db.entities.Users
import com.evan.dokan.ui.auth.ImageUpdateActivity
import com.evan.dokan.ui.home.cart.CartFragment
import com.evan.dokan.ui.home.cart.ICartCountListener
import com.evan.dokan.ui.home.dashboard.DashboardFragment
import com.evan.dokan.ui.home.dashboard.product.ProductCategoryWiseListFragment
import com.evan.dokan.ui.home.dashboard.product.details.ProductDetailsFragment
import com.evan.dokan.ui.home.dashboard.search.ProductSearchFragment
import com.evan.dokan.ui.home.order.OrderFragment
import com.evan.dokan.ui.home.order.details.OrderDetailsFragment
import com.evan.dokan.ui.home.settings.SettingsFragment
import com.evan.dokan.ui.home.settings.password.ChangePasswordFragment
import com.evan.dokan.ui.home.settings.profile.ProfileUpdateFragment
import com.evan.dokan.ui.home.wishlist.IWishCountListener
import com.evan.dokan.ui.home.wishlist.WishListFragment
import com.evan.dokan.util.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_layout.*
import kotlinx.android.synthetic.main.bottom_navigation_layout.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.io.File
import java.io.IOException

class HomeActivity : AppCompatActivity() ,KodeinAware,IWishCountListener,ICartCountListener{
    var mFragManager: FragmentManager? = null
    var fragTransaction: FragmentTransaction? = null
    var mCurrentFrag: Fragment? = null
    var CURRENT_PAGE: Int? = 1
    var shop_user_id:Int?=0
    var shop_user_name:String?=""
    override val kodein by kodein()
    private val factory : HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    var token:String?=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        viewModel.cartCountListener=this
        viewModel.wishCountListener=this
        token = SharedPreferenceUtil.getShared(this, SharedPreferenceUtil.TYPE_AUTH_TOKEN)

        shop_user_id=intent.getIntExtra("ShopUserId",0)
        shop_user_name=intent.getStringExtra("ShopUserName")
        viewModel.countCartList(token!!,shop_user_id!!)
        viewModel.countWishList(token!!,shop_user_id!!)
        Log.e("shop_user_id","shop_user_id: "+shop_user_id)
        setUpHeader(FRAG_TOP)
        afterClickTabItem(FRAG_TOP, null)
        setUpFooter(FRAG_TOP)
        img_header_back?.setOnClickListener {
            onBackPressed()
        }
        img_back?.setOnClickListener {
            finish()
        }
        btn_orders?.setOnClickListener {
            setUpHeader(FRAG_ORDER)
            afterClickTabItem(FRAG_ORDER, null)
            setUpFooter(FRAG_ORDER)
        }
    }
    fun onCount(){
        viewModel.countCartList(token!!,shop_user_id!!)
        viewModel.countWishList(token!!,shop_user_id!!)
    }
    fun btn_home_clicked(view: View) {
        setUpHeader(FRAG_TOP)
        afterClickTabItem(FRAG_TOP, null)
        setUpFooter(FRAG_TOP)

    }

    fun btn_store_clicked(view: View) {
        setUpHeader(FRAG_STORE)
        afterClickTabItem(FRAG_STORE, null)
        setUpFooter(FRAG_STORE)
        //ccheckPP()


    }

    fun btn_wish_list_clicked(view: View) {
        setUpHeader(FRAG_WISHLIST)
        afterClickTabItem(FRAG_WISHLIST, null)
        setUpFooter(FRAG_WISHLIST)

    }

    fun btn_settings_clicked(view: View) {
        setUpHeader(FRAG_SETTINGS)
        afterClickTabItem(FRAG_SETTINGS, null)
        setUpFooter(FRAG_SETTINGS)
    }

    @Suppress("UNUSED_PARAMETER")
    fun afterClickTabItem(fragId: Int, obj: Any?) {
        if(fragId==7){
            addFragment(fragId, true, obj)
        }
        else{
            addFragment(fragId, false, obj)
        }

    }
    fun goToProductListCategoryFragment() {
        setUpHeader(FRAG_CATEGORY)
        addFragment(FRAG_CATEGORY, true, null)

    }
    fun goToChangePasswordFragment(users: Users) {
        setUpHeader(FRAG_CHANGE_PASSWORD)
        mFragManager = supportFragmentManager
        var fragId:Int?=0
        fragId=FRAG_CHANGE_PASSWORD
        fragTransaction = mFragManager?.beginTransaction()
        val count = mFragManager?.getBackStackEntryCount()
        if (count != 0) {

        }
        if (mCurrentFrag != null && mCurrentFrag!!.getTag() != null && mCurrentFrag!!.getTag() == fragId.toString()) {
            return
        }
        var newFrag: Fragment? = null
        newFrag = ChangePasswordFragment()
        val b= Bundle()
        b.putParcelable(Users::class.java?.getSimpleName(), users)
        newFrag.setArguments(b)
        mCurrentFrag = newFrag
        fragTransaction!!.setCustomAnimations(
            R.anim.view_transition_in_left,
            R.anim.view_transition_out_left,
            R.anim.view_transition_in_right,
            R.anim.view_transition_out_right
        )

        fragTransaction?.replace(R.id.main_container, newFrag!!, fragId.toString())
        fragTransaction?.addToBackStack(fragId.toString())
        fragTransaction!!.commit()

    }
    fun goToProductSearchFragment() {
        setUpHeader(FRAG_SEARCH)
        addFragment(FRAG_SEARCH, true, null)

    }
    override fun onBackPressed() {
        super.onBackPressed()
        val f = getVisibleFragment()
        if (f != null)
        {
            if (f is DashboardFragment) {
                val dashboard: DashboardFragment =
                    mFragManager?.findFragmentByTag(FRAG_TOP.toString()) as DashboardFragment
                dashboard.removeChild()
                setUpHeader(FRAG_TOP)
            }
            else if (f is ProductCategoryWiseListFragment) {
                val productCategoryWiseListFragment: ProductCategoryWiseListFragment =
                    mFragManager?.findFragmentByTag(FRAG_CATEGORY.toString()) as ProductCategoryWiseListFragment
                productCategoryWiseListFragment.removeChild()
                setUpHeader(FRAG_CATEGORY)
            }
           else if (f is WishListFragment) {
                val storeFragment: WishListFragment =
                    mFragManager?.findFragmentByTag(FRAG_WISHLIST.toString()) as WishListFragment
                storeFragment.removeChild()
                setUpHeader(FRAG_WISHLIST )
            }
            else if (f is CartFragment) {
                val cartFragment: CartFragment =
                    mFragManager?.findFragmentByTag(FRAG_STORE.toString()) as CartFragment
               // cartFragment.removeChild()
                setUpHeader(FRAG_STORE )
            }
            else if (f is OrderFragment) {
                val orderFragment: OrderFragment =
                    mFragManager?.findFragmentByTag(FRAG_ORDER.toString()) as OrderFragment
                //storeFragment.removeChild()
                setUpHeader(FRAG_ORDER )
            }
            else if (f is SettingsFragment) {

                setUpHeader(FRAG_SETTINGS )
            }
//            if (f is SupplierFragment) {
//                val supplierFragment: SupplierFragment =
//                    mFragManager?.findFragmentByTag(FRAG_SUPPLIER.toString()) as SupplierFragment
//                supplierFragment.replace()
//                supplierFragment.removeChild()
//                setUpHeader(FRAG_SUPPLIER)
//            }
//            if (f is PurchaseFragment) {
//                val purchaseFragment: PurchaseFragment =
//                    mFragManager?.findFragmentByTag(FRAG_PURCHASE.toString()) as PurchaseFragment
//                purchaseFragment.replace()
//                purchaseFragment.removeChild()
//                setUpHeader(FRAG_PURCHASE)
//            }
//            if (f is ProductFragment) {
//                val productFragment: ProductFragment =
//                    mFragManager?.findFragmentByTag(FRAG_PRODUCT.toString()) as ProductFragment
//                productFragment.replace()
//                productFragment.removeChild()
//                setUpHeader(FRAG_PRODUCT)
//            }
        }

    }
    fun backPress() {
        hideKeyboard(this)
        onBackPressed()

    }
    fun finishs(){
        finishAffinity()
    }
    fun goToProfileUpdateFragment(users: Users) {
        setUpHeader(FRAG_PROFILE_UPDATE)
        mFragManager = supportFragmentManager
        var fragId:Int?=0
        fragId=FRAG_PROFILE_UPDATE
        fragTransaction = mFragManager?.beginTransaction()
        val count = mFragManager?.getBackStackEntryCount()
        if (count != 0) {

        }
        if (mCurrentFrag != null && mCurrentFrag!!.getTag() != null && mCurrentFrag!!.getTag() == fragId.toString()) {
            return
        }
        var newFrag: Fragment? = null
        newFrag = ProfileUpdateFragment()
        val b= Bundle()
        b.putParcelable(Users::class.java?.getSimpleName(), users)
        newFrag.setArguments(b)
        mCurrentFrag = newFrag
        fragTransaction!!.setCustomAnimations(
            R.anim.view_transition_in_left,
            R.anim.view_transition_out_left,
            R.anim.view_transition_in_right,
            R.anim.view_transition_out_right
        )

        fragTransaction?.replace(R.id.main_container, newFrag!!, fragId.toString())
        fragTransaction?.addToBackStack(fragId.toString())
        fragTransaction!!.commit()

    }
    fun goToOrderDetailsFragment(order: Order) {
        setUpHeader(FRAG_ORDER_DETAILS)
        mFragManager = supportFragmentManager
        // create transaction
        var fragId:Int?=0
        fragId=FRAG_ORDER_DETAILS
        fragTransaction = mFragManager?.beginTransaction()
        //check if there is any backstack if yes then remove it
        val count = mFragManager?.getBackStackEntryCount()
        if (count != 0) {
            //this will clear the back stack and displays no animation on the screen
            // mFragManager?.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        // check current fragment is wanted fragment
        if (mCurrentFrag != null && mCurrentFrag!!.getTag() != null && mCurrentFrag!!.getTag() == fragId.toString()) {
            return
        }
        var newFrag: Fragment? = null

        // identify which fragment will be called

        newFrag = OrderDetailsFragment()
        val b= Bundle()
        b.putParcelable(Order::class.java?.getSimpleName(), order)
//        var list: ArrayList<Product> = arrayListOf()
//        b.putParcelableArrayList(Product::class.java?.getSimpleName(), list)

        newFrag.setArguments(b)

        mCurrentFrag = newFrag

        fragTransaction!!.setCustomAnimations(
            R.anim.view_transition_in_left,
            R.anim.view_transition_out_left,
            R.anim.view_transition_in_right,
            R.anim.view_transition_out_right
        )

        // param 1: container id, param 2: new fragment, param 3: fragment id

        fragTransaction?.replace(R.id.main_container, newFrag!!, fragId.toString())
        // prevent showed when user press back fabReview
        fragTransaction?.addToBackStack(fragId.toString())
        //  fragTransaction?.hide(active).show(guideFragment).commit();
        fragTransaction!!.commit()

    }
    fun goToProductDetailsFragment(product: Product) {
        setUpHeader(FRAG_PRODUCT)
        mFragManager = supportFragmentManager
        // create transaction
        var fragId:Int?=0
        fragId=FRAG_PRODUCT
        fragTransaction = mFragManager?.beginTransaction()
        //check if there is any backstack if yes then remove it
        val count = mFragManager?.getBackStackEntryCount()
        if (count != 0) {
            //this will clear the back stack and displays no animation on the screen
            // mFragManager?.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        // check current fragment is wanted fragment
        if (mCurrentFrag != null && mCurrentFrag!!.getTag() != null && mCurrentFrag!!.getTag() == fragId.toString()) {
            return
        }
        var newFrag: Fragment? = null

        // identify which fragment will be called

        newFrag = ProductDetailsFragment()
        val b= Bundle()
        b.putParcelable(Product::class.java?.getSimpleName(), product)
//        var list: ArrayList<Product> = arrayListOf()
//        b.putParcelableArrayList(Product::class.java?.getSimpleName(), list)

        newFrag.setArguments(b)

        mCurrentFrag = newFrag

        fragTransaction!!.setCustomAnimations(
            R.anim.view_transition_in_left,
            R.anim.view_transition_out_left,
            R.anim.view_transition_in_right,
            R.anim.view_transition_out_right
        )

        // param 1: container id, param 2: new fragment, param 3: fragment id

        fragTransaction?.replace(R.id.main_container, newFrag!!, fragId.toString())
        // prevent showed when user press back fabReview
        fragTransaction?.addToBackStack(fragId.toString())
        //  fragTransaction?.hide(active).show(guideFragment).commit();
        fragTransaction!!.commit()

    }
    fun addFragment(fragId: Int, isHasAnimation: Boolean, obj: Any?) {
        // init fragment manager
        mFragManager = supportFragmentManager
        // create transaction
        fragTransaction = mFragManager?.beginTransaction()
        //check if there is any backstack if yes then remove it
        val count = mFragManager?.getBackStackEntryCount()
        if (count != 0) {
            //this will clear the back stack and displays no animation on the screen
            // mFragManager?.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        // check current fragment is wanted fragment
        if (mCurrentFrag != null && mCurrentFrag!!.getTag() != null && mCurrentFrag!!.getTag() == fragId.toString()) {
            return
        }
        var newFrag: Fragment? = null
        // identify which fragment will be called
        when (fragId) {
            FRAG_TOP -> {
                newFrag = DashboardFragment()
            }
            FRAG_STORE -> {
                newFrag = CartFragment()
            }
            FRAG_WISHLIST -> {
                newFrag = WishListFragment()
            }
            FRAG_SETTINGS -> {
                newFrag = SettingsFragment()
            }
            FRAG_CATEGORY-> {
                newFrag = ProductCategoryWiseListFragment()
            }
            FRAG_ORDER-> {
                newFrag = OrderFragment()
            }
            FRAG_SEARCH-> {
                newFrag = ProductSearchFragment()
            }
        }
        val b= Bundle()
        b.putInt("ShopUserId", shop_user_id!!)
        b.putString("ShopUserName", shop_user_name!!)
        newFrag?.setArguments(b)
        mCurrentFrag = newFrag
        // init argument
        if (obj != null) {
            val args = Bundle()
        }
        // set animation
        if (isHasAnimation) {
            fragTransaction!!.setCustomAnimations(
                R.anim.view_transition_in_left,
                R.anim.view_transition_out_left,
                R.anim.view_transition_in_right,
                R.anim.view_transition_out_right
            )
        }
        // param 1: container id, param 2: new fragment, param 3: fragment id

        fragTransaction?.replace(R.id.main_container, newFrag!!, fragId.toString())
        // prevent showed when user press back fabReview
        fragTransaction?.addToBackStack(fragId.toString())
        //  fragTransaction?.hide(active).show(guideFragment).commit();
        fragTransaction!!.commit()

    }
    fun setUpHeader(type: Int) {
        when (type) {
            FRAG_TOP -> {
                ll_back_header?.visibility = View.GONE
                rlt_header?.visibility = View.VISIBLE
                tv_title.text = resources.getString(R.string.home)
                btn_footer_home.setSelected(true)
            }
            FRAG_STORE -> {
                ll_back_header?.visibility = View.GONE
                rlt_header?.visibility = View.VISIBLE
                tv_title.text = resources.getString(R.string.cart)
                btn_footer_store.setSelected(true)
            }
            FRAG_WISHLIST -> {
                ll_back_header?.visibility = View.GONE
                rlt_header?.visibility = View.VISIBLE
                btn_footer_wish_list.setSelected(true)
                tv_title.text = resources.getString(R.string.wish_list)

            }
            FRAG_SETTINGS -> {
                ll_back_header?.visibility = View.GONE
                rlt_header?.visibility = View.VISIBLE
                tv_title.text = resources.getString(R.string.my_page)
                btn_footer_settings.setSelected(true)

            }
            FRAG_CATEGORY -> {
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.product)
                btn_footer_home.setSelected(true)

            }
            FRAG_PRODUCT -> {
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.details)
                btn_footer_home.setSelected(true)

            }
            FRAG_ORDER_DETAILS -> {
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.details)
                btn_footer_home.setSelected(true)

            }
            FRAG_PROFILE_UPDATE -> {
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.update)
                btn_footer_settings.setSelected(true)

            }
            FRAG_CHANGE_PASSWORD -> {
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.change)
                btn_footer_settings.setSelected(true)

            }

            FRAG_SEARCH -> {
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.product)
                btn_footer_home.setSelected(true)

            }

            FRAG_ORDER -> {
                ll_back_header?.visibility = View.VISIBLE
                rlt_header?.visibility = View.GONE
                tv_details.text = resources.getString(R.string.order)


            }
            else -> {

            }
        }
    }
    fun getVisibleFragment(): Fragment? {
        val fragmentManager = mFragManager
        val fragments = fragmentManager!!.fragments
        fragments.reverse()
        for (fragment in fragments!!) {
            if (fragment != null && fragment.isVisible) {
                return fragment
            }
        }
        return null
    }
    fun setUpFooter(type: Int) {
        setUnselectAllmenu()
        CURRENT_PAGE = type

        when (type) {
            FRAG_TOP -> {
                shadow_line?.visibility = View.VISIBLE
                bottom_navigation?.visibility = View.VISIBLE

                btn_footer_home.setSelected(true)
                tv_home_menu.setSelected(true)
            }
            FRAG_STORE -> {
                shadow_line?.visibility = View.VISIBLE
                bottom_navigation?.visibility = View.VISIBLE

                btn_footer_store.setSelected(true)
                tv_store_menu.setSelected(true)
            }
            FRAG_WISHLIST -> {
                shadow_line?.visibility = View.VISIBLE
                bottom_navigation?.visibility = View.VISIBLE

                btn_footer_wish_list.setSelected(true)
                tv_wish_list_menu.setSelected(true)
            }
            FRAG_SETTINGS-> {
                shadow_line?.visibility = View.VISIBLE
                bottom_navigation?.visibility = View.VISIBLE

                btn_footer_settings.setSelected(true)
                tv_settings_menu.setSelected(true)
            }
            FRAG_NOTICE -> {
                shadow_line?.visibility = View.VISIBLE
                bottom_navigation?.visibility = View.VISIBLE


            }


            else -> {

            }

        }
    }

    private fun setUnselectAllmenu() {
        btn_footer_home.setSelected(false)
        tv_home_menu.setSelected(false)

        btn_footer_store.setSelected(false)
        tv_store_menu.setSelected(false)

        btn_footer_wish_list.setSelected(false)
        tv_wish_list_menu.setSelected(false)

        btn_footer_settings.setSelected(false)
        tv_settings_menu.setSelected(false)
    }

    fun setAnimation(isAnimation: Boolean) {
        if (isAnimation) {
            fragTransaction!!.setCustomAnimations(
                R.anim.view_transition_in_left,
                R.anim.view_transition_out_right, R.anim.view_transition_in_left,
                R.anim.view_transition_out_right
            )
        }
    }

    override fun onWishCount(count: Int) {
        if(count==0){
            tv_wish_count?.visibility=View.GONE
        }
        else{
            tv_wish_count?.visibility=View.VISIBLE
        }
        tv_wish_count?.text=count.toString()
    }

    override fun onCartCount(count: Int) {
        if(count==0){
            tv_cart_count?.visibility=View.GONE
        }
        else{
            tv_cart_count?.visibility=View.VISIBLE
        }
        tv_cart_count?.text=count.toString()
    }
    private val CAMERA_PERMISSION_REQUEST_CODE = 1001
    private val RESULT_TAKE_PHOTO = 10
    private val RESULT_LOAD_IMG = 101
    private val REQUEST_EXTERNAL_STORAGE_FROM_CAPTURE = 1002
    private val RESULT_UPDATE_IMAGE = 11

    fun openImageChooser() {
        showImagePickerDialog(this, object :
            DialogActionListener {
            override fun onPositiveClick() {
                openCamera()
            }

            override fun onNegativeClick() {
                checkGalleryPermission()
            }
        })
    }
    private fun checkGalleryPermission() {
        if (isCameraePermissionGranted(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                        arrayOf<String?>(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        REQUEST_EXTERNAL_STORAGE_FROM_CAPTURE
                    )
                } else {
                    //start your camera

                    getImageFromGallery()
                }
            } else {
                getImageFromGallery()
            }
        } else {
            //required permission

            ActivityCompat.requestPermissions(
                this,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE_FROM_CAPTURE
            )
        }
    }
    fun openCamera() {
        if (isCameraePermissionGranted(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED&&checkSelfPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                        arrayOf<String?>(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE),
                        CAMERA_PERMISSION_REQUEST_CODE
                    )
                } else {
                    //start your camera

                    takePhoto()
                }
            } else {
                takePhoto()
            }
        } else {
            //required permission

            ActivityCompat.requestPermissions(
                this,
                PERMISSIONS_CAMERA,
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }
    }

    // Receive the permissions request result
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Start your camera handling here

                    takePhoto()
                }
            }
            REQUEST_EXTERNAL_STORAGE_FROM_CAPTURE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Start your camera handling here
                    getImageFromGallery()
                }
            }
        }
    }

    private var mTakeUri: Uri? = null
    private var mFile: File? = null
    private var mCurrentPhotoPath: String? = null
    private fun takePhoto() {
        val intent =
            Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            // Create the File where the photo should go
            mFile = null
            try {
                mFile = createImageFile(this)
                mCurrentPhotoPath = mFile?.getAbsolutePath()
            } catch (ex: IOException) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created


            if (mFile != null) {
                mTakeUri = FileProvider.getUriForFile(
                    this,
                    BuildConfig.APPLICATION_ID, mFile!!
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                } else {
                    val packageManager: PackageManager =
                        this.getPackageManager()
                    val activities: List<ResolveInfo> =
                        packageManager.queryIntentActivities(
                            intent,
                            PackageManager.MATCH_DEFAULT_ONLY
                        )
                    for (resolvedIntentInfo in activities) {
                        val packageName: String? =
                            resolvedIntentInfo.activityInfo.packageName
                        this.grantUriPermission(
                            packageName,
                            mTakeUri,
                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        )
                    }
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mTakeUri)
                startActivityForResult(intent, RESULT_TAKE_PHOTO)
            }
        }
    }

    private fun getImageFromGallery() {
        val photoPickerIntent =
            Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG)
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Check which request we're responding to
        Log.e("requestCode", resultCode.toString() + " requestCode" + requestCode)
        Log.e("RESULT_OK", "RESULT_OK" + RESULT_OK)


        when (requestCode) {
            RESULT_LOAD_IMG -> {
                try {
                    val imageUri = data?.data
                    if (imageUri != null) {
                        val file = File(getRealPathFromURI(imageUri, this))
                        goImagePreviewPage(imageUri, file)
                    }
                } catch (e: Exception) {
                    Log.e("exc", "" + e.message)
                    Toast.makeText(this,"Can not found this image", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
            RESULT_TAKE_PHOTO -> {
                //return if photopath is null
                if(mCurrentPhotoPath == null)
                    return
                mCurrentPhotoPath = getRightAngleImage(mCurrentPhotoPath!!)
                try {
                    val imgFile = File(mCurrentPhotoPath)
                    if (imgFile.exists()) {
                        goImagePreviewPage(mTakeUri, imgFile)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            RESULT_UPDATE_IMAGE -> {
                if (data != null && data?.hasExtra("updated_image_url")) {
                    val updated_image_url: String? = data?.getStringExtra("updated_image_url")
                    Log.e("updated_image_url", "--$updated_image_url")
                    if (updated_image_url != null) {
                        if (updated_image_url == "") {

                        } else {
                            //update in
                            val f = getVisibleFragment()
                            if (f != null) {
                                if (f is ProfileUpdateFragment) {

                                    f.showImage(updated_image_url)
                                }

                            }



                        }
                    }
                }
            }





        }

    }
    fun goImagePreviewPage(uri: Uri?, imageFile: File) {
        val fileSize = imageFile.length().toInt()
        if (fileSize <= SERVER_SEND_FILE_SIZE_MAX_LIMIT) {
            val i = Intent(
                this,
                ImageUpdateActivity::class.java
            )
            i.putExtra("from", FRAG_CREATE_NEW_DELIVERY)
            temporary_profile_picture = imageFile
            temporary_profile_picture_uri = uri
            startActivityForResult(i, RESULT_UPDATE_IMAGE)
            overridePendingTransition(R.anim.right_to_left, R.anim.stand_by)
        } else {
            showDialogSuccessMessage(
                this,
                resources.getString(R.string.image_size_is_too_large),
                resources.getString(R.string.txt_close),


                null
            )
        }
    }
}
