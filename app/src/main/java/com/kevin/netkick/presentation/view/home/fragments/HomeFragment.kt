package com.kevin.netkick.presentation.view.home.fragments

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevin.netkick.Utils
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
        val onlineCheck = activity?.let { Utils.isOnline(it) }
        if (onlineCheck == true){
            getLiveData()
        }

    }

    private fun getLiveData() {
        lifecycleScope.launch {
            mainViewModel.getLiveMatches(Utils.LIVE_PARAMS).collectLatest {
                liveScoreAdapter = LiveScoreAdapter(it.response,application)
                binding.rvLivescore.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                binding.rvLivescore.adapter = liveScoreAdapter
            }
        }
    }
}