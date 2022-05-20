package com.example.applicationinternship.ui.movies.viewpager.Popular

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationinternship.R
import com.example.applicationinternship.databinding.FragmentNowPlayingBinding
import com.example.applicationinternship.databinding.FragmentPopularBinding
import com.example.applicationinternship.network.RetrofitInstance
import com.example.applicationinternship.ui.movies.viewpager.NowPlaying.NowPlayingAdapter
import com.example.applicationinternship.ui.movies.viewpager.NowPlaying.NowPlayingViewModel
import kotlinx.android.synthetic.main.fragment_now_playing.*
import kotlinx.android.synthetic.main.fragment_popular.*


class PopularFragment : Fragment() {


    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!
    private var page_number:Int = 1
    private var page_number_final:Int=1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_movies_list_popular.addOnScrollListener(scrollListener)
        sendData(RetrofitInstance.FIRST_PAGE)
    }

    private fun sendData(page_number:Int){
        _binding.apply {
            if (page_number <= page_number_final) {
                rv_movies_list_popular.layoutManager = LinearLayoutManager(this@PopularFragment.context)
                val popularViewModel = ViewModelProvider(this@PopularFragment)[PopularViewModel::class.java]
                popularViewModel.movieLiveData.observe(viewLifecycleOwner) {
                    rv_movies_list_popular.adapter = PopularAdapter(it.movieList)
                    page_number_final = it.totalPages
                }
                popularViewModel.fetchMoviePopular(page_number)
            }
        }
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= page_number_final
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isScrolling
            if(shouldPaginate) {

                page_number++
                sendData(page_number)
                isScrolling = false
            } else {
                rv_movies_list_popular.setPadding(0, 0, 0, 0)
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}