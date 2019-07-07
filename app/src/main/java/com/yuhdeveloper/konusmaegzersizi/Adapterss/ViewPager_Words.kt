package com.yuhdeveloper.konusmaegzersizi.Adapterss

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ViewPager_Words(fm:FragmentManager,fragments:ArrayList<Fragment>,lstTitle:ArrayList<String>): FragmentPagerAdapter(fm) {

    var fragment = fragments
    var titles = lstTitle


    override fun getItem(p0: Int): Fragment? {
       return fragment[p0]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    override fun getCount(): Int {
       return fragment.size
    }

}