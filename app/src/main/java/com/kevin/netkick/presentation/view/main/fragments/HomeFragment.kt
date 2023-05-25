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
import com.kevin.netkick.presentation.view.main.activity.MainActivity
import com.kevin.netkick.presentation.view.viewmodels.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HomeFragment() : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var liveScoreAdapter: LiveScoreAdapter
    private lateinit var popularTeamsAdapter: PopularTeamsPreviewAdapter
    private lateinit var newsAdapter: NewsHeadlinePreviewAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = (activity as MainActivity).provideMainViewModel()
        checkOnline(true)
        setupAdapters()
        binding.tvSeeAllPopularTeams.setOnClickListener {
            val intentPopular = Intent(requireContext(), PopularTeamsListActivity::class.java)
            requireContext().startActivity(intentPopular)
        }
        binding.tvSeeAllNews.setOnClickListener {
            val intentNews = Intent(requireContext(), AllNewsListActivity::class.java)
            requireContext().startActivity(intentNews)
        }
    }

    private fun setupAdapters() {
        binding.apply {
            val pageSnapHelper = PagerSnapHelper()
            liveScoreAdapter = LiveScoreAdapter(mutableListOf(), false)
            rvLivescore.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvLivescore.adapter = liveScoreAdapter
            pageSnapHelper.attachToRecyclerView(binding.rvLivescore)

            popularTeamsAdapter = PopularTeamsPreviewAdapter(
                mutableListOf(),
                PresentationUtils.POPULAR_SEASON
            )
            rvPopularTeams.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvPopularTeams.adapter = popularTeamsAdapter

            val noScrollLayoutManager = object : LinearLayoutManager(requireContext()) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
            newsAdapter = NewsHeadlinePreviewAdapter(mutableListOf(), true)
            rvNewsHeadline.layoutManager = noScrollLayoutManager
            rvNewsHeadline.adapter = newsAdapter
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
        getLiveMatches()
        getPopularTeams()
        getNewsHeadline()
    }

    private fun getPopularTeams() {
        lifecycleScope.launch {
            mainViewModel.getPopularTeams()
            mainViewModel.popularTeamsFlow.collectLatest {
                val popularTeamsPreview = if (it.response.size > 14) {
                    it.response.slice(0..14)
                } else listOf()
                if (it.error.isNotBlank()) {
                    PresentationUtils.errorToast(requireContext(), it.error)
                } else {
                    popularTeamsAdapter.addDataToList(
                        popularTeamsPreview,
                        PresentationUtils.POPULAR_SEASON
                    )
                }
            }
        }
    }

    private fun getNewsHeadline() {
        lifecycleScope.launch {
            mainViewModel.getNewsHeadline()
            mainViewModel.newsHeadlineFlow.collectLatest {
                setupNewsLayout(it)
                if (it.error.isNotBlank()) {
                    PresentationUtils.errorToast(requireContext(), it.error)
                } else {
                    setupNewsLayout(it)
                }
            }
        }
    }

    private fun setupNewsLayout(data: NewsResponse) {
        when {
            data.totalResults == 0 -> {
                binding.apply {
                    tvSeeAllNews.visibility = View.INVISIBLE
                    newsAdapter.addDataToList(data.articles.toMutableList(), true)
                }
            }
            data.totalResults <= 4 -> {
                binding.apply {
                    tvSeeAllNews.visibility = View.INVISIBLE
                    newsAdapter.addDataToList(data.articles.toMutableList(), false)
                }
            }
            else -> {
                binding.apply {
                    tvSeeAllNews.visibility = View.VISIBLE
                    newsAdapter.addDataToList(data.articles.slice(0..3).toMutableList(), false)
                }
            }
        }

    }


    private fun getLiveMatches() {
        lifecycleScope.launch {
            mainViewModel.getLiveMatches(PresentationUtils.LIVE_PARAMS)
            mainViewModel.liveScoreFlow.collectLatest {
                if (it.error.isNotBlank()) {
                    PresentationUtils.errorToast(requireContext(), it.error)
                } else {
                    liveScoreAdapter.addDataToList(it.response, it.response.isEmpty())
                }
            }
        }

    }
}