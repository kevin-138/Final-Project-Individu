package com.kevin.netkick.presentation.view.explore.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevin.netkick.NetkickApplication
import com.kevin.netkick.R
import com.kevin.netkick.databinding.ActivityLeagueStandingsBinding
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.adapters.LeagueAdapter
import com.kevin.netkick.presentation.adapters.LeagueStandingsAdapter
import com.kevin.netkick.presentation.view.viewmodels.ExploreViewModel
import com.kevin.netkick.presentation.view.viewmodels.factory.ViewModelFactory
import javax.inject.Inject

class LeagueStandingsActivity : AppCompatActivity() {
    lateinit var binding: ActivityLeagueStandingsBinding
    private lateinit var adapter: LeagueStandingsAdapter
    private lateinit var progressBar: AlertDialog

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ExploreViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as NetkickApplication).appComponent.injectIntoLeagueStandingActivity(this)
        super.onCreate(savedInstanceState)
        binding = ActivityLeagueStandingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAdapter()
        binding.ibBackButton.setOnClickListener {
            finish()
        }
        checkOnline()
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
        if (!intent.getStringExtra(PresentationUtils.LEAGUE_FULL_DATA).isNullOrEmpty()){
//            setObserver()
            setLayout()
        }
    }

    private fun setLayout() {
        binding.apply {

        }
    }

    private fun setupAdapter() {
        binding.apply {
            adapter = LeagueStandingsAdapter(mutableListOf())
            rvStandings.layoutManager = LinearLayoutManager(this@LeagueStandingsActivity)
            rvStandings.adapter = adapter
        }
    }

    private fun setObserver() {
        viewModel.standingResults.observe(this){
            adapter.addDataToList(it.response[0].league.standings[0].group)
        }
    }

    private fun setProgressBar() {
        progressBar = AlertDialog.Builder(this).setCancelable(false).setView(R.layout.loading).create()
        progressBar.show()
        progressBar.window?.setLayout(400, 400)
        progressBar.window?.setBackgroundDrawableResource(R.drawable.connection_dialog_background)
    }
}