package com.kevin.netkick.presentation.view.home.fragments

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.databinding.FragmentHomeBinding
import com.kevin.netkick.presentation.adapters.LiveScoreAdapter
import com.kevin.netkick.presentation.view.viewmodels.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HomeFragment(private val mainViewModel: MainViewModel, private val application: Application) : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var liveScoreAdapter: LiveScoreAdapter
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

    }

    fun checkOnline(current:Boolean = false) {
        val onlineCheck = activity?.let { PresentationUtils.isOnline(requireActivity()) }
        if (onlineCheck == true){
            if (binding.rvLivescore.adapter == null) {
                getLiveData()
            }
        }else{
            if (current){
                PresentationUtils.networkDialog(requireActivity(),PresentationUtils.HOME)
            }
        }
    }

    private fun getLiveData() {
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