package com.kevin.netkick.presentation.view.Main.fragments

import android.app.Application
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
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.adapters.LiveScoreAdapter
import com.kevin.netkick.presentation.adapters.PopularTeamsPreviewAdapter
import com.kevin.netkick.presentation.view.home.news.activity.AllNewsListActivity
import com.kevin.netkick.presentation.view.home.popularteams.activity.PopularTeamsListActivity
import com.kevin.netkick.presentation.view.viewmodels.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HomeFragment(private val mainViewModel: MainViewModel, private val application: Application) : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var liveScoreAdapter: LiveScoreAdapter
    private lateinit var popularTeamsAdapter: PopularTeamsPreviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkOnline(true)

        binding.tvSeeAllPopularTeams.setOnClickListener {
            val intentPopular = Intent(requireContext(),PopularTeamsListActivity::class.java)
            requireContext().startActivity(intentPopular)
        }
        binding.tvSeeAllNews.setOnClickListener {
            val intentNews = Intent(requireContext(),AllNewsListActivity::class.java)
            requireContext().startActivity(intentNews)
        }
    }

    fun checkOnline(current:Boolean = false) {
        val onlineCheck = activity?.let { PresentationUtils.isOnline(requireActivity()) }
        if (onlineCheck == true){
            if (
                binding.rvLivescore.adapter == null && binding.rvPopularTeams.adapter == null
            ) {
                getLiveData()
            }
        }else{
            if (current){
                PresentationUtils.networkDialog(requireActivity(),PresentationUtils.HOME)
            }
        }
    }

    private fun getLiveData() {
        getLiveMatches()
        getPopularTeams()
    }

    fun getPopularTeams(){
        lifecycleScope.launch {
            mainViewModel.getPopularTeams()
            mainViewModel.popularTeamsFlow.collectLatest {
                val popularTeamsPreview = it.response.slice(0..14)
                popularTeamsAdapter = PopularTeamsPreviewAdapter(popularTeamsPreview)
                binding.rvPopularTeams.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
                binding.rvPopularTeams.adapter = popularTeamsAdapter
            }
        }
    }

    fun getLiveMatches(){
        val pageSnapHelper = PagerSnapHelper()
        lifecycleScope.launch {
            mainViewModel.getLiveMatches(PresentationUtils.LIVE_PARAMS)
            mainViewModel.liveScoreFlow.collectLatest {
                liveScoreAdapter = LiveScoreAdapter(it.response,"UH OH",application)
                binding.rvLivescore.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                binding.rvLivescore.adapter = liveScoreAdapter
                pageSnapHelper.attachToRecyclerView(binding.rvLivescore)
            }
        }
    }
}