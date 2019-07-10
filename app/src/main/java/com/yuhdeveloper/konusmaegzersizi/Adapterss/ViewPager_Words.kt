package com.yuhdeveloper.konusmaegzersizi.Adapterss

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.yuhdeveloper.konusmaegzersizi.WordsFragment

class ViewPager_Words(fm: FragmentManager, fragments:ArrayList<Fragment>, lstTitle:ArrayList<String>): FragmentPagerAdapter(fm) {

    var fragment = fragments
    var titles = lstTitle


    override fun getItem(p0: Int): Fragment {
       return fragment[p0]
    }
    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
    override fun getCount(): Int {
       return fragment.size
    }
}