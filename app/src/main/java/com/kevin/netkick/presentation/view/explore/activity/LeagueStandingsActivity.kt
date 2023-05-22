package com.kevin.netkick.presentation.view.explore.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.kevin.netkick.NetkickApplication
import com.kevin.netkick.R
import com.kevin.netkick.databinding.ActivityLeagueStandingsBinding
import com.kevin.netkick.domain.entity.league.ResponseL
import com.kevin.netkick.presentation.PresentationUtils
import com.kevin.netkick.presentation.adapters.LeagueStandingsAdapter
import com.kevin.netkick.presentation.view.viewmodels.ExploreViewModel
import com.kevin.netkick.presentation.view.viewmodels.factory.ViewModelFactory
import javax.inject.Inject

class LeagueStandingsActivity : AppCompatActivity() {
    lateinit var binding: ActivityLeagueStandingsBinding
    private lateinit var adapter: LeagueStandingsAdapter
    private lateinit var progressBar: AlertDialog
    private var leagueData: ResponseL? = null

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
            leagueData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(PresentationUtils.LEAGUE_FULL_DATA, ResponseL::class.java)
            }else{
                intent.getParcelableExtra(PresentationUtils.LEAGUE_FULL_DATA)
            }

            if (leagueData != null){
            setObserver()
            setLayout(leagueData!!)
            setSpinner(leagueData!!)

        }
    }

    private fun setSpinner(leagueData: ResponseL) {
        val listSeason = leagueData.seasons.map {
            it.year
        }
        val arrayAdapter = ArrayAdapter(this, com.bumptech.glide.R.layout.support_simple_spinner_dropdown_item, listSeason)
        binding.apply {
            spLeagueSeason.adapter = arrayAdapter

            spLeagueSeason.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val leagueSeasonSelected = leagueData.seasons.filter {
                        it.year == listSeason[position]
                    }
                    if (leagueSeasonSelected[0].coverage.standings){
                        noStandingsData(false)
                        getOnlineData(leagueData.league.id, listSeason[position])
                        setGroup()
                    }else{
                        noStandingsData(true)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    val leagueSeasonDefault = leagueData.seasons.filter {
                        it.year == listSeason[0]
                    }
                    if (leagueSeasonDefault[0].coverage.standings){
                        noStandingsData(false)
                        getOnlineData(leagueData.league.id, listSeason[0])
                        setGroup()
                    }else{
                        noStandingsData(true)
                    }
                }
            }

        }
    }

    private fun setGroup() {
        binding.apply {
            val size = adapter.getGroupSize()
            var group = 0
            ibGroupNext.setOnClickListener {
                if (group != size-1){
                    group += 1
                    adapter.setGroup(group)
                    tvLeagueGroupTitle.text = "Group $group"
                }
            }
            ibGroupPrev.setOnClickListener {
                if (group != 0){
                    group -= 1
                    adapter.setGroup(group)
                    tvLeagueGroupTitle.text = "Group $group"
                }
            }

        }
    }

    private fun noStandingsData(boolean: Boolean){
        if (boolean){
            binding.apply {
                tvNoStandings.visibility = View.VISIBLE
                hsvStandings.visibility = View.INVISIBLE
            }
        }else{
            binding.apply {
                tvNoStandings.visibility = View.INVISIBLE
                hsvStandings.visibility = View.VISIBLE
            }
        }
    }

    private fun getOnlineData(id:Int, season:Int) {
        viewModel.setSearchQuery(Pair(id,season))
    }

    private fun setLayout(data:ResponseL) {
        val loadingDrawable1 = CircularProgressDrawable(this)
        loadingDrawable1.strokeWidth = 5f
        loadingDrawable1.centerRadius = 30f
        loadingDrawable1.setColorSchemeColors(Color.WHITE)
        loadingDrawable1.start()

        binding.apply {
                tvLeagueName.text = data.league.name
                tvLeagueType.text = data.league.type

            Glide.with(this@LeagueStandingsActivity)
                .load(data.league.logo)
                .placeholder(loadingDrawable1)
                .error(R.drawable.broken_image_icon)
                .into(ivLeagueLogoStandings)

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
            adapter.addDataToList(it.response[0].league.standings)
        }
    }

    private fun setProgressBar() {
        progressBar = AlertDialog.Builder(this).setCancelable(false).setView(R.layout.loading).create()
        progressBar.show()
        progressBar.window?.setLayout(400, 400)
        progressBar.window?.setBackgroundDrawableResource(R.drawable.connection_dialog_background)
    }
}