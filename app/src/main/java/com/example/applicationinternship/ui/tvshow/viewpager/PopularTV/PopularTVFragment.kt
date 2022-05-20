package com.example.applicationinternship.ui.tvshow.viewpager.PopularTV

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationinternship.R
import com.example.applicationinternship.databinding.FragmentHomeBinding
import com.example.applicationinternship.databinding.FragmentPopularTVBinding
import com.example.applicationinternship.network.RetrofitInstance
import com.example.applicationinternship.ui.home.HomeAdapter
import com.example.applicationinternship.ui.home.HomeFragment
import com.example.applicationinternship.ui.home.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_popular_t_v.*

class PopularTVFragment : Fragment() {

    private val TAG = PopularTVFragment::class.java.simpleName

    private var _binding: FragmentPopularTVBinding? = null
    private val binding get() = _binding!!
    private var page_number:Int = 1
    private var page_number_final:Int=1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularTVBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_tv_list_popular.addOnScrollListener(scrollListener)
        sendData(RetrofitInstance.FIRST_PAGE)

    }

    private fun sendData(page_number:Int){
        _binding.apply {
            if (page_number <= page_number_final) {
                rv_tv_list_popular.layoutManager = LinearLayoutManager(this@PopularTVFragment.context)
                val populartvViewModel = ViewModelProvider(this@PopularTVFragment)[PopularTVViewModel::class.java]
                populartvViewModel.movieLiveData.observe(viewLifecycleOwner) {
                    var movieAdapter = HomeAdapter(it.movieList)
                    rv_tv_list_popular.adapter = movieAdapter
                    movieAdapter.onItemClick = {
                        System.out.println("daaaaa")
                    }

                    page_number_final = it.totalPages
                }
                populartvViewModel.fetchPopularTV(page_number)
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
                rv_tv_list_popular.setPadding(0, 0, 0, 0)
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
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