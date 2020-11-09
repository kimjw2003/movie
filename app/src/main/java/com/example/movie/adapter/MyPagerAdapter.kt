package com.example.movie.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.movie.tabFragment.FirstFragment
import com.example.movie.tabFragment.SecondFragment
import com.example.movie.tabFragment.ThirdFragment

class MyPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) { //switch()문과 동일하다.
            0 -> {
                FirstFragment()
            }
            1 -> {
                SecondFragment()
            }
            else -> {return ThirdFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3 //3개니깐
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> "영화정보"
            1 -> "풀연진/감독"
            else -> {return "관련영상"}
        }
    }

}