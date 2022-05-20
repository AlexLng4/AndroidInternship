package com.example.applicationinternship.ui.tvshow.viewpager.TvShow

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.applicationinternship.databinding.FragmentTvshowBinding
import com.example.applicationinternship.ui.tvshow.PagerAdapterTVShow
import com.google.android.material.tabs.TabLayoutMediator

class TVShowFragment : Fragment() {

    private lateinit var _binding: FragmentTvshowBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTvshowBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding.apply {
            viewPagerTVShow.adapter = PagerAdapterTVShow(this@TVShowFragment)
            TabLayoutMediator(tabLayoutTVShow, viewPagerTVShow) { tab, index ->
                tab.text = when (index) {
                    0 -> {
                        "TV ON THE AIR"
                    }
                    1 -> {
                        "POPULAR"
                    }
                    2 -> {
                        "TOP RATED"
                    }
                    else -> {
                        throw Resources.NotFoundException("Position Not Found")
                    }
                }
            }.attach()
        }
    }
}