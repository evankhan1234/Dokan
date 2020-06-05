package com.evan.dokan.ui.home.newsfeed.publicpost

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.evan.dokan.R
import com.evan.dokan.ui.home.HomeActivity
import com.evan.dokan.ui.home.HomeViewModel
import com.evan.dokan.util.SharedPreferenceUtil
import com.evan.dokan.util.image_update
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.*


class CommentsFragment : BottomSheetDialogFragment() {

    private var mBehavior: BottomSheetBehavior<*>? = null

    var img_close:ImageView?=null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =
            super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        val view =
            View.inflate(context, R.layout.fragment_comments, null)
        img_close=view.findViewById(R.id.img_close)
        dialog.setContentView(view)

        mBehavior =
            BottomSheetBehavior.from(view.parent as View)

        img_close?.setOnClickListener {
            dismiss()
        }
        return dialog
    }
    override fun onStart() {
        super.onStart()
        mBehavior?.setState(BottomSheetBehavior.STATE_EXPANDED)
    }
}