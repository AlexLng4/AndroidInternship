package com.example.applicationinternship.ui.movies

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.applicationinternship.databinding.FragmentMoviesBinding
import com.example.applicationinternship.ui.movies.viewpager.PagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MoviesFragment : Fragment() {

    private lateinit var _binding: FragmentMoviesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding.apply {
            viewPagerMovies.adapter = PagerAdapter(this@MoviesFragment)
            TabLayoutMediator(tabLayoutMovies, viewPagerMovies) { tab, index ->
                tab.text = when (index) {
                    0 -> {
                        "NOW PLAYING"
                    }
                    1 -> {
                        "POPULAR"
                    }
                    2 -> {
                        "UPCOMING"
                    }
                    else -> {
                        throw Resources.NotFoundException("Position Not Found")
                    }
                }
            }.attach()
        }
    }
}