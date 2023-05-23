package com.kevin.netkick.presentation.view.explore.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevin.netkick.NetkickApplication
import com.kevin.netkick.R
import com.kevin.netkick.databinding.ActivityLeagueSearchBinding
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.adapters.LeagueAdapter
import com.kevin.netkick.presentation.view.viewmodels.ExploreViewModel
import com.kevin.netkick.presentation.view.viewmodels.factory.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule


class LeagueSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLeagueSearchBinding
    private lateinit var adapter: LeagueAdapter
    private lateinit var progressBar: AlertDialog

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ExploreViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as NetkickApplication).appComponent.injectIntoLeagueSearchActivity(this)
        super.onCreate(savedInstanceState)
        binding = ActivityLeagueSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAdapter()
        binding.ibBackButton.setOnClickListener {
            finish()
        }
        checkOnline()

    }

    private fun setObserver() {
        viewModel.searchResults.observe(this){
            if (it.response.isEmpty()){
                layoutVisibility(true)
            }else{
                layoutVisibility(false)
                adapter.addDataToList(it.response)
            }
        }
    }

    private fun layoutVisibility(isEmpty: Boolean) {
        binding.apply {
            when(isEmpty){
                true -> {
                    tvNothingFound.visibility = View.VISIBLE
                    rvCountriesLeague.visibility = View.INVISIBLE
                }
                else -> {
                    tvNothingFound.visibility = View.INVISIBLE
                    rvCountriesLeague.visibility = View.VISIBLE
                }
            }
        }

    }

    private fun setupAdapter() {
        binding.apply {
            adapter = LeagueAdapter(mutableListOf())
            rvCountriesLeague.layoutManager = LinearLayoutManager(this@LeagueSearchActivity)
            rvCountriesLeague.adapter = adapter
        }
    }

    private fun checkOnline() {
        val onlineCheck = PresentationUtils.isOnline(this)
        if (onlineCheck) {
            checkIntent()
        } else {
            PresentationUtils.networkDialog(this)
        }
    }

    private fun checkIntent(){
        if (intent.getStringExtra(PresentationUtils.COUNTRY_CODE).isNullOrEmpty()){
            setObserver()
            setSearchBar(false)
        }else{
            setProgressBar()
            getData()
            setSearchBar(true)
        }
    }

    private fun getData() {
        lifecycleScope.launch {
            intent.getStringExtra(PresentationUtils.COUNTRY_CODE)
                ?.let { viewModel.getLeagueFilterCountry(it) }
            viewModel.leagueByCountryFlow.collectLatest {
                adapter.addDataToList(it.response)
                progressBar.dismiss()
            }
        }

    }

    private fun setSearchBar(filteredByCountry:Boolean) {
        binding.apply {
            svLeague.setOnQueryTextListener(object : SearchView.OnQueryTextListener,android.widget.SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String): Boolean {
                    if (!filteredByCountry){
                        if (query.length < 3) {
                            Toast.makeText(this@LeagueSearchActivity,"Search Field must be at least 3 characters!",
                                Toast.LENGTH_SHORT).show()
                        }else{
                            setProgressBar()
                            viewModel.setSearchQuery(query)
                            Timer().schedule(2000L) {
                                progressBar.dismiss()
                            }
                        }
                    }
                    return false
                }

                override fun onQueryTextChange(inputTxt: String): Boolean {
                    if (filteredByCountry){
                        when(adapter.filterData(inputTxt)){
                            1 -> binding.apply {
                                tvNothingFound.visibility = View.VISIBLE
                                rvCountriesLeague.visibility = View.INVISIBLE
                            }

                            2 -> binding.apply {
                                tvNothingFound.visibility = View.INVISIBLE
                                rvCountriesLeague.visibility = View.VISIBLE
                            }
                        }
                    }
                    return false
                }
            })
        }
    }

    private fun setProgressBar() {
        progressBar = AlertDialog.Builder(this).setCancelable(false).setView(R.layout.loading).create()
        progressBar.show()
        progressBar.window?.setLayout(400, 400)
        progressBar.window?.setBackgroundDrawableResource(R.drawable.connection_dialog_background)
    }



}