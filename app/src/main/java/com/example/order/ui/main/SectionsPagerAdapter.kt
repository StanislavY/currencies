package com.example.order.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

import com.example.order.R

private val TAB_TITLES = arrayOf(
    R.string.popular_currencies_list_title,
    R.string.favorite_title
)

class SectionsPagerAdapter(private val context: MainFragment, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {

        return MainFragment.newInstance(position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {

        return 2
    }
}