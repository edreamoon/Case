package com.ccino.demo.view

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class EntryPageAdapter(activity: TabActivity) : FragmentStateAdapter(activity) {
    private val fragments: List<Fragment> =
        listOf(BlankFragment.newInstance(), BlankFragment.newInstance(), BlankFragment.newInstance(),
            BlankFragment.newInstance()/*, BlankFragment.newInstance(), BlankFragment.newInstance()*/)

    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int): Fragment = fragments[position]
}