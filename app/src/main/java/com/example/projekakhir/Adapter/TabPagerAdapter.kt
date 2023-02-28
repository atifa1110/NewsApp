package com.example.projekakhir.Adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.projekakhir.Fragment.*

class TabPagerAdapter (activity: AppCompatActivity) : FragmentStateAdapter(activity){

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
               return HomeFragment()
            }
            1 -> {
                return SportsFragment()
            }
            2 -> {
                return HealthFragment()
            }
            3 -> {
                return ScienceFragment()
            }
            4 -> {
                return EntertainmentFragment()
            }
            5 -> {
                return TechnoFragment()
            }
            else -> return HomeFragment()
        }
    }

    override fun getItemCount(): Int {
        return 6
    }

}