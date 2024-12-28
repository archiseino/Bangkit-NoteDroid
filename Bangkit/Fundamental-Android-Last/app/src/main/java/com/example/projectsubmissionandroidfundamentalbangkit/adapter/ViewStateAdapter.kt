package com.example.projectsubmissionandroidfundamentalbangkit.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.projectsubmissionandroidfundamentalbangkit.ui.view.FollowerFragment
import com.example.projectsubmissionandroidfundamentalbangkit.ui.view.FollowingFragment

class ViewStateAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0){
            FollowerFragment()
        }else{
            FollowingFragment()
        }

    }


}
