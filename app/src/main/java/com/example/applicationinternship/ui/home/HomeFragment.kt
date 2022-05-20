package com.example.applicationinternship.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationinternship.databinding.FragmentHomeBinding

import com.example.applicationinternship.network.RetrofitInstance.FIRST_PAGE
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    
    private val TAG = HomeFragment::class.java.simpleName

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var page_number:Int = 1
    private var page_number_final:Int=1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
            _binding = FragmentHomeBinding.inflate(inflater, container, false)
            return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_movies_list.addOnScrollListener(scrollListener)
        sendData(FIRST_PAGE)

    }

    private fun sendData(page_number:Int){
        _binding.apply {
            if (page_number <= page_number_final) {
                rv_movies_list.layoutManager = LinearLayoutManager(this@HomeFragment.context)
                val homeViewModel = ViewModelProvider(this@HomeFragment)[HomeViewModel::class.java]
                homeViewModel.movieLiveData.observe(viewLifecycleOwner) {
                    var movieAdapter = HomeAdapter(it.movieList)
                    rv_movies_list.adapter = movieAdapter
                    movieAdapter.onItemClick = {
                        System.out.println("daaaaa")
                    }

                    page_number_final = it.totalPages
                }
                homeViewModel.fetchMovie(page_number)
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

                Log.d(TAG, "onScrolled: should paginate == true")

                page_number++
                sendData(page_number)
                isScrolling = false
            } else {
                rv_movies_list.setPadding(0, 0, 0, 0)
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState==AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
                Log.d(TAG, "onScrollStateChanged: isScrolling = true")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}