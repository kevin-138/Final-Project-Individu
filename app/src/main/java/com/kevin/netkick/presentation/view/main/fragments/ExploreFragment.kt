package com.kevin.netkick.presentation.view.main.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.kevin.netkick.databinding.FragmentExploreBinding
import com.kevin.netkick.domain.entity.country.CountryC
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.adapters.CountriesAdapter
import com.kevin.netkick.presentation.view.explore.activity.LeagueSearchActivity
import com.kevin.netkick.presentation.view.viewmodels.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ExploreFragment(private val mainViewModel: MainViewModel) : Fragment() {
    private lateinit var binding: FragmentExploreBinding
    private lateinit var adapter: CountriesAdapter
    private lateinit var dataList: ArrayList<CountryC>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExploreBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataList = ArrayList()
        setAdapter()
        setSearchBar()
        checkOnline(false)
        binding.btnSkip.setOnClickListener {
            val intent = Intent(requireContext(), LeagueSearchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setAdapter() {
        adapter = CountriesAdapter(dataList)
        binding.apply {
            rvCountriesLeague.layoutManager = GridLayoutManager(requireContext(),3)
            rvCountriesLeague.adapter = adapter
        }
    }

    private fun setSearchBar() {
        binding.apply {
            svCountry.setOnQueryTextListener(object : SearchView.OnQueryTextListener,android.widget.SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(inputTxt: String): Boolean {
                    adapter.filterData(inputTxt)
                    return false
                }
            })
        }
    }

    fun checkOnline(current:Boolean = false) {
        val onlineCheck = activity?.let { PresentationUtils.isOnline(requireActivity()) }
        if (onlineCheck == true){
            if (!mainViewModel.runnedExplore){
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
                adapter.addDataToList(it.response)
            }
        }
    }



}