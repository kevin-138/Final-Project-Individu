package com.kevin.netkick.presentation.view.main.fragments

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.kevin.netkick.R
import com.kevin.netkick.databinding.FragmentExploreBinding
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.adapters.CountriesAdapter
import com.kevin.netkick.presentation.view.viewmodels.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ExploreFragment(private val mainViewModel: MainViewModel) : Fragment() {
    private lateinit var binding: FragmentExploreBinding
    private lateinit var adapter: CountriesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExploreBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkOnline(false)
    }

    fun checkOnline(current:Boolean = false) {
        val onlineCheck = activity?.let { PresentationUtils.isOnline(requireActivity()) }
        if (onlineCheck == true){
            if (binding.rvCountriesLeague.adapter == null){
                getLiveData()
            }
        }else{
            if (current){
                PresentationUtils.networkDialog(requireActivity(),PresentationUtils.EXPLORE)
            }
        }
    }

    private fun getLiveData(){
        lifecycleScope.launch {
            mainViewModel.getAllCountries()
            mainViewModel.allCountriesFlow.collectLatest {
                adapter = CountriesAdapter(it.response)
                binding.apply {
                    rvCountriesLeague.layoutManager = GridLayoutManager(requireContext(),3)
                    rvCountriesLeague.adapter = adapter
                }
            }
        }
    }



}