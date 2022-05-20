package com.example.applicationinternship.ui.tvshow

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.applicationinternship.ui.tvshow.viewpager.TvShow.TVShowFragment
import com.example.applicationinternship.ui.tvshow.viewpager.PopularTV.PopularTVFragment
import com.example.applicationinternship.ui.tvshow.viewpager.TvOnTheAir.TVOnTheAirFragment
import com.example.applicationinternship.ui.tvshow.viewpager.TopRatedFragment

class PagerAdapterTVShow(fragmentActivity: TVShowFragment): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> { TVOnTheAirFragment() }
            1 -> { PopularTVFragment() }
            2 -> { TopRatedFragment() }
            else -> {throw  Resources.NotFoundException("Position Not Found")}
        }
    }
}