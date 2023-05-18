package com.kevin.netkick.presentation.view.general.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevin.netkick.NetkickApplication
import com.kevin.netkick.R
import com.kevin.netkick.databinding.ActivityLeagueSearchBinding
import com.kevin.netkick.domain.entity.country.CountryC
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.adapters.LeagueAdapter
import com.kevin.netkick.presentation.view.viewmodels.ExploreViewModel
import com.kevin.netkick.presentation.view.viewmodels.factory.ViewModelFactory
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule


class LeagueSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLeagueSearchBinding
    private lateinit var adapter: LeagueAdapter
    private lateinit var dataList: ArrayList<CountryC>
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
//        setProgressBar()
        setupAdapter()
        setObserver()
        binding.ibBackButton.setOnClickListener {
            finish()
        }
        checkOnline()

    }

    private fun setObserver() {
        viewModel.searchResults.observe(this){
            adapter.addDataToList(it.response)
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
            setSearchBar()
        } else {
            PresentationUtils.networkDialog(this)
        }
    }
    private fun setSearchBar() {
        binding.apply {
            svLeague.setOnQueryTextListener(object : SearchView.OnQueryTextListener,android.widget.SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String): Boolean {
                    if (intent.getStringExtra(PresentationUtils.COUNTRY_CODE).isNullOrEmpty()){
                        if (query.length < 3) {
                            Toast.makeText(this@LeagueSearchActivity,"Search Field must be at least 3 characters!",
                                Toast.LENGTH_SHORT).show()
                        }else{
                            setProgressBar()
                            viewModel.setSearchQuery(query)
                            Timer().schedule(1000L) {
                                progressBar.dismiss()
                            }
                        }
                    }else{

                    }
                    return false
                }

                override fun onQueryTextChange(inputTxt: String): Boolean {
//                    filterList(inputTxt)
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