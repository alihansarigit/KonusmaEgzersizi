package com.yuhdeveloper.konusmaegzersizi.viewPagerSample

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.yuhdeveloper.konusmaegzersizi.R
import kotlinx.android.synthetic.main.fragment_view_pager_sample.*

class ViewPagerSampleFragment : DialogFragment(), OnPagerNumberChangeListener, View.OnClickListener {
    private lateinit var viewPager: ViewPager

    private lateinit var pagerIndicator: IndefinitePagerIndicator
    private lateinit var verticalPagerIndicator: IndefinitePagerIndicator
    private lateinit var previousButton: Button
    private lateinit var nextButton: Button
    private var pagerAdapter: ViewPagerAdapter? = null
    private var isVerticalEnabled = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_view_pager_sample, container, false)

//        isVerticalEnabled = context.getSharedPreferences(
//            MainActivity.SHARED_PREFERENCES,
//            AppCompatActivity.MODE_PRIVATE
//        ).getBoolean(
//            MainActivity.isVerticalIndicatorKeyPreference,
//            false
//        )

        bindViews(view)
        setupViews()

        return view
    }

    private fun bindViews(view: View) {
        viewPager = view.findViewById(R.id.viewpager)
        pagerIndicator = view.findViewById(R.id.viewpager_pager_indicator)
        previousButton = view.findViewById(R.id.viewpager_previous_button)
        nextButton = view.findViewById(R.id.viewpager_next_button)
        verticalPagerIndicator = view.findViewById(R.id.viewpager_vertical_pager_indicator)
        previousButton.visibility=View.INVISIBLE
    }

    private fun setupViews() {
        pagerAdapter = ViewPagerAdapter(context!!)
        viewPager.adapter = pagerAdapter
        if (isVerticalEnabled) {
            verticalPagerIndicator.attachToViewPager(viewPager)
            verticalPagerIndicator.visibility = View.VISIBLE
        } else {
            pagerIndicator.attachToViewPager(viewPager)
            pagerIndicator.visibility = View.VISIBLE
        }

        previousButton.setOnClickListener(this)
        nextButton.setOnClickListener(this)
    }

    override fun onPagerNumberChanged() {
        pagerAdapter?.notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.viewpager_previous_button -> {
                    viewpager_next_button.setText("İLERİ")
                if (viewPager.currentItem == 0) {
//                    viewPager.currentItem = viewPager.adapter!!.count - 1
                } else {
                    viewPager.currentItem = viewPager.currentItem - 1
                    if(viewPager.currentItem == 0){
                        viewpager_previous_button.visibility=View.INVISIBLE
                    }
                }
            }
            R.id.viewpager_next_button -> {

                if (viewPager.currentItem == viewPager.adapter!!.count - 1) {
//                    viewPager.currentItem = 0
//                    viewpager_next_button.setText("BİTİR")
                    dismiss()

                } else {
                    viewPager.currentItem = viewPager.currentItem + 1
                    if(viewPager.currentItem==viewPager.adapter!!.count-1){
                        viewpager_next_button.setText("BİTİR")
                    }
                }
                viewpager_previous_button.visibility=View.VISIBLE
            }
        }
    }
}
