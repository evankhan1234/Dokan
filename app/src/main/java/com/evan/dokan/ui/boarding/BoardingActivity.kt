package com.evan.dokan.ui.boarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.evan.bazar.ui.boarding.IntroSlide
import com.evan.bazar.ui.boarding.SliderAdapter
import com.evan.dokan.R
import com.evan.dokan.ui.auth.LoginActivity
import kotlinx.android.synthetic.main.activity_boarding.*

class BoardingActivity : AppCompatActivity() {

    private val slider_adapter= SliderAdapter(this,listOf(
        IntroSlide("Shopping","Go Around The World","https://www.techversantinfotech.com/wp-content/uploads/2017/02/Our-Services01-.png"),
        IntroSlide("Customer","Based on the characteristics","https://www.clipartmax.com/png/full/202-2024299_software-engineer-icons-software-development-icon-png.png"),
        IntroSlide("Selling","Number of goods or services","https://5.imimg.com/data5/GC/IW/TK/GLADMIN-4625004/selection-075-500x500.png"), IntroSlide("Order","Achieve a particular thing","https://cdn2.vectorstock.com/i/1000x1000/27/11/beautiful-young-lady-sitting-alone-in-restaurant-vector-25072711.jpg")
    ))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boarding)

        view_pager.adapter=slider_adapter
        indicator()
        setCurrentIndex(0)
        view_pager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndex(position)
            }
        })
        btn_next?.setOnClickListener {
            if(view_pager?.currentItem!!+1<slider_adapter.itemCount){
                view_pager.currentItem += 1
            }
            else{
                Intent(this, LoginActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        }
        tv_skip?.setOnClickListener {
            Intent(this, LoginActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
    }

    fun indicator(){
        val indicators= arrayOfNulls<ImageView>(slider_adapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams= LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8,0,8,0)
        for(i in indicators.indices){
            indicators[i]= ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.indicator_inactive))
                this?.layoutParams=layoutParams
            }
            linear_category?.addView(indicators[i])
        }

    }
    fun setCurrentIndex(index:Int){
        val childCount=linear_category.childCount
        for(i in 0 until childCount){
            val imageView=linear_category[i]as ImageView
            if(i==index){
                imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.indicator_active))
            }
            else{
                imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.indicator_inactive))
            }

        }
    }
}
