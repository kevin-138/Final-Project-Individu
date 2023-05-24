package com.kevin.netkick.presentation.view.main.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.kevin.netkick.databinding.FragmentHomeBinding
import com.kevin.netkick.domain.entity.news.NewsResponse
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.adapters.LiveScoreAdapter
import com.kevin.netkick.presentation.adapters.NewsHeadlinePreviewAdapter
import com.kevin.netkick.presentation.adapters.PopularTeamsPreviewAdapter
import com.kevin.netkick.presentation.view.home.news.activity.AllNewsListActivity
import com.kevin.netkick.presentation.view.home.popularteams.activity.PopularTeamsListActivity
import com.kevin.netkick.presentation.view.viewmodels.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HomeFragment(private val mainViewModel: MainViewModel) : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var liveScoreAdapter: LiveScoreAdapter
    private lateinit var popularTeamsAdapter: PopularTeamsPreviewAdapter
    private lateinit var newsAdapter: NewsHeadlinePreviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkOnline(true)

        binding.tvSeeAllPopularTeams.setOnClickListener {
            val intentPopular = Intent(requireContext(), PopularTeamsListActivity::class.java)
            requireContext().startActivity(intentPopular)
        }
        binding.tvSeeAllNews.setOnClickListener {
            val intentNews = Intent(requireContext(), AllNewsListActivity::class.java)
            requireContext().startActivity(intentNews)
        }
    }

    fun checkOnline(current: Boolean = false) {
        val onlineCheck = activity?.let { PresentationUtils.isOnline(requireActivity()) }
        if (onlineCheck == true) {
            if (!mainViewModel.runnedHome) {
                getLiveData()
            }
        } else {
            if (current) {
                PresentationUtils.networkDialog(requireActivity(), PresentationUtils.HOME)
            }
        }
    }

    private fun getLiveData() {
//        getLiveMatches()
//        getPopularTeams()
        getNewsHeadline()
    }

    private fun getPopularTeams() {
        lifecycleScope.launch {
            mainViewModel.getPopularTeams()
            mainViewModel.popularTeamsFlow.collectLatest {
                val popularTeamsPreview = if (it.response.size > 14){it.response.slice(0..14) } else listOf()
                popularTeamsAdapter = PopularTeamsPreviewAdapter(popularTeamsPreview,PresentationUtils.POPULAR_SEASON)
                binding.apply {
                    rvPopularTeams.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    rvPopularTeams.adapter = popularTeamsAdapter
                }
            }
        }
    }

    private fun getNewsHeadline() {
        lifecycleScope.launch {
            mainViewModel.getNewsHeadline()
            mainViewModel.newsHeadlineFlow.collectLatest {
                setupNewsLayout(it)
            }
        }
    }

    private fun setupNewsLayout(data: NewsResponse) {
        when {
            data.totalResults == 0 -> {
                binding.apply {
                    tvSeeAllNews.visibility = View.INVISIBLE
                    newsAdapter = NewsHeadlinePreviewAdapter(data.articles.toMutableList(), true)
                    setupNewsAdapter(newsAdapter)
                }
            }
            data.totalResults <= 4 -> {
                binding.apply {
                    tvSeeAllNews.visibility = View.INVISIBLE
                    newsAdapter = NewsHeadlinePreviewAdapter(data.articles.toMutableList(), false)
                    setupNewsAdapter(newsAdapter)
                }
            }
            else -> {
                binding.apply {
                    tvSeeAllNews.visibility = View.VISIBLE
                    newsAdapter = NewsHeadlinePreviewAdapter(data.articles.slice(0..3).toMutableList(), false)
                    setupNewsAdapter(newsAdapter)
                }
            }
        }

    }

    fun setupNewsAdapter(newsAdapter: NewsHeadlinePreviewAdapter) {
        val noScrollLayoutManager = object : LinearLayoutManager(requireContext()) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        binding.apply {
            rvNewsHeadline.layoutManager = noScrollLayoutManager
            rvNewsHeadline.adapter = newsAdapter
        }
    }



    private fun getLiveMatches() {
        val pageSnapHelper = PagerSnapHelper()
        lifecycleScope.launch {
            mainViewModel.getLiveMatches(PresentationUtils.LIVE_PARAMS)
            mainViewModel.liveScoreFlow.collectLatest {
                liveScoreAdapter = LiveScoreAdapter(it.response, it.response.isEmpty())
                binding.rvLivescore.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                binding.rvLivescore.adapter = liveScoreAdapter
                pageSnapHelper.attachToRecyclerView(binding.rvLivescore)
            }
        }

    }
}